package com.benimhesabim.app.feature.transaction

import com.benimhesabim.app.domain.model.Transaction

data class TransactionListUiState(
    val transactions: List<Transaction> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)
