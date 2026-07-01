package com.benimhesabim.app.data.repository

import com.benimhesabim.app.core.common.DateFormatter
import com.benimhesabim.app.data.local.dao.TransactionDao
import com.benimhesabim.app.data.mapper.TransactionDtoMapper
import com.benimhesabim.app.data.mapper.TransactionMapper
import com.benimhesabim.app.data.remote.supabase.SupabaseRemoteDataSource
import com.benimhesabim.app.domain.model.MonthlySummary
import com.benimhesabim.app.domain.model.SyncStatus
import com.benimhesabim.app.domain.model.Transaction
import com.benimhesabim.app.domain.repository.TransactionRepository
import java.time.YearMonth
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class TransactionRepositoryImpl @Inject constructor(
    private val dao: TransactionDao,
    private val remoteDataSource: SupabaseRemoteDataSource
) : TransactionRepository {
    override fun observeTransactions(): Flow<List<Transaction>> =
        dao.getAllTransactions().map { list ->
            list.map(TransactionMapper::toDomain)
                .filterNot { it.syncStatus == SyncStatus.PENDING_DELETE }
        }

    override fun observeTransactionsByMonth(yearMonth: YearMonth): Flow<List<Transaction>> {
        val (start, end) = DateFormatter.monthBounds(yearMonth)
        return dao.getTransactionsByMonth(start, end).map { list ->
            list.map(TransactionMapper::toDomain)
                .filterNot { it.syncStatus == SyncStatus.PENDING_DELETE }
        }
    }

    override fun observePendingSyncTransactions(): Flow<List<Transaction>> =
        dao.getPendingSyncTransactions().map { list -> list.map(TransactionMapper::toDomain) }

    override suspend fun addTransaction(transaction: Transaction): Result<Unit> = runCatching {
        val local = TransactionMapper.toEntity(
            transaction.copy(syncStatus = SyncStatus.PENDING_CREATE)
        )
        dao.insertTransaction(local)
        syncOne(transaction.copy(syncStatus = SyncStatus.PENDING_CREATE))
    }

    override suspend fun updateTransaction(transaction: Transaction): Result<Unit> = runCatching {
        val local = TransactionMapper.toEntity(
            transaction.copy(syncStatus = SyncStatus.PENDING_UPDATE)
        )
        dao.updateTransaction(local)
        syncOne(transaction.copy(syncStatus = SyncStatus.PENDING_UPDATE))
    }

    override suspend fun deleteTransaction(transactionId: String): Result<Unit> = runCatching {
        val existing = dao.getTransactionById(transactionId) ?: return@runCatching
        if (existing.syncStatus == SyncStatus.PENDING_CREATE.name) {
            dao.deleteTransaction(existing)
        } else {
            val pendingDelete = existing.copy(syncStatus = SyncStatus.PENDING_DELETE.name)
            dao.updateTransaction(pendingDelete)
            syncOne(TransactionMapper.toDomain(pendingDelete))
        }
    }

    override fun observeMonthlySummary(yearMonth: YearMonth): Flow<MonthlySummary> {
        val source = observeTransactionsByMonth(yearMonth)
        return source.map { transactions ->
            val totalIncome = transactions.filter { it.type == com.benimhesabim.app.domain.model.TransactionType.INCOME }.sumOf { it.amountMinor }
            val totalExpense = transactions.filter { it.type == com.benimhesabim.app.domain.model.TransactionType.EXPENSE }.sumOf { it.amountMinor }
            MonthlySummary(
                totalIncomeMinor = totalIncome,
                totalExpenseMinor = totalExpense,
                balanceMinor = totalIncome - totalExpense
            )
        }
    }

    override suspend fun syncPendingTransactions(): Result<Unit> = runCatching {
        dao.getPendingSyncTransactions().first().forEach { transaction ->
            syncOne(TransactionMapper.toDomain(transaction))
        }
    }

    private suspend fun syncOne(transaction: Transaction) {
        val success = when (transaction.syncStatus) {
            SyncStatus.PENDING_DELETE -> {
                remoteDataSource.deleteTransaction(transaction.id).isSuccess
            }
            else -> {
                remoteDataSource.upsertTransaction(TransactionDtoMapper.toDto(transaction)).isSuccess
            }
        }

        if (success) {
            if (transaction.syncStatus == SyncStatus.PENDING_DELETE) {
                dao.deleteTransaction(TransactionMapper.toEntity(transaction))
            } else {
                dao.updateTransaction(TransactionMapper.toEntity(transaction.copy(syncStatus = SyncStatus.SYNCED)))
            }
        }
    }
}
