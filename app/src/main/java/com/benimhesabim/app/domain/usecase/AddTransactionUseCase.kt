package com.benimhesabim.app.domain.usecase

import com.benimhesabim.app.domain.model.Transaction
import com.benimhesabim.app.domain.repository.TransactionRepository
import javax.inject.Inject

class AddTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transaction: Transaction) = repository.addTransaction(transaction)
}
