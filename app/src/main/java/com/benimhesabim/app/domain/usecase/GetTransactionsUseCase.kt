package com.benimhesabim.app.domain.usecase

import com.benimhesabim.app.domain.repository.TransactionRepository
import java.time.YearMonth
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(yearMonth: YearMonth? = null) = when (yearMonth) {
        null -> repository.observeTransactions()
        else -> repository.observeTransactionsByMonth(yearMonth)
    }
}
