package com.benimhesabim.app.feature.transaction

import com.benimhesabim.app.core.common.TransactionCategories
import com.benimhesabim.app.domain.model.TransactionType
import java.time.LocalDate

data class AddTransactionUiState(
    val type: TransactionType = TransactionType.EXPENSE,
    val title: String = "",
    val amountText: String = "",
    val category: String = TransactionCategories.expense.first(),
    val date: LocalDate = LocalDate.now(),
    val note: String = "",
    val isSaving: Boolean = false,
    val errorMessage: String? = null,
    val saved: Boolean = false
)
