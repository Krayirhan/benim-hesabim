package com.benimhesabim.app.feature.home

import com.benimhesabim.app.domain.model.MonthlySummary
import com.benimhesabim.app.domain.model.Transaction

data class HomeUiState(
    val email: String? = null,
    val summary: MonthlySummary = MonthlySummary(0, 0, 0),
    val recentTransactions: List<Transaction> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)
