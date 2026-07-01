package com.benimhesabim.app.domain.usecase

import com.benimhesabim.app.domain.repository.TransactionRepository
import javax.inject.Inject

class DeleteTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transactionId: String) = repository.deleteTransaction(transactionId)
}
