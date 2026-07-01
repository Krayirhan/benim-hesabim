package com.benimhesabim.app.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benimhesabim.app.domain.repository.AuthRepository
import com.benimhesabim.app.domain.repository.TransactionRepository
import com.benimhesabim.app.domain.usecase.GetMonthlySummaryUseCase
import com.benimhesabim.app.domain.usecase.GetTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.YearMonth
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val getMonthlySummaryUseCase: GetMonthlySummaryUseCase,
    private val authRepository: AuthRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val user = authRepository.currentUser()
            val summaryFlow = getMonthlySummaryUseCase(YearMonth.now())
            val recentFlow = getTransactionsUseCase().combine(summaryFlow) { transactions, summary ->
                HomeUiState(
                    email = user?.email,
                    summary = summary,
                    recentTransactions = transactions.take(5),
                    isLoading = false
                )
            }
            recentFlow.collect { _uiState.value = it }
        }
        viewModelScope.launch {
            runCatching { transactionRepository.syncPendingTransactions() }
        }
    }
}
