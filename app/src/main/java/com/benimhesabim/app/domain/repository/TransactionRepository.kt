package com.benimhesabim.app.domain.repository

import com.benimhesabim.app.domain.model.MonthlySummary
import com.benimhesabim.app.domain.model.Transaction
import java.time.YearMonth
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun observeTransactions(): Flow<List<Transaction>>
    fun observeTransactionsByMonth(yearMonth: YearMonth): Flow<List<Transaction>>
    fun observePendingSyncTransactions(): Flow<List<Transaction>>
    suspend fun addTransaction(transaction: Transaction): Result<Unit>
    suspend fun updateTransaction(transaction: Transaction): Result<Unit>
    suspend fun deleteTransaction(transactionId: String): Result<Unit>
    fun observeMonthlySummary(yearMonth: YearMonth): Flow<MonthlySummary>
    suspend fun syncPendingTransactions(): Result<Unit>
}
