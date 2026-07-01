package com.benimhesabim.app.feature.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benimhesabim.app.core.common.AmountFormatter
import com.benimhesabim.app.core.util.AppClock
import com.benimhesabim.app.domain.model.SyncStatus
import com.benimhesabim.app.domain.model.Transaction
import com.benimhesabim.app.domain.model.TransactionType
import com.benimhesabim.app.domain.usecase.AddTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val addTransactionUseCase: AddTransactionUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddTransactionUiState())
    val uiState: StateFlow<AddTransactionUiState> = _uiState.asStateFlow()

    fun onTypeChange(type: TransactionType) {
        _uiState.update {
            it.copy(
                type = type,
                category = if (type == TransactionType.INCOME) {
                    com.benimhesabim.app.core.common.TransactionCategories.income.first()
                } else {
                    com.benimhesabim.app.core.common.TransactionCategories.expense.first()
                }
            )
        }
    }

    fun onTitleChange(value: String) = _uiState.update { it.copy(title = value, errorMessage = null, saved = false) }
    fun onAmountChange(value: String) = _uiState.update { it.copy(amountText = value, errorMessage = null, saved = false) }
    fun onCategoryChange(value: String) = _uiState.update { it.copy(category = value, errorMessage = null, saved = false) }
    fun onDateChange(value: java.time.LocalDate) = _uiState.update { it.copy(date = value, errorMessage = null, saved = false) }
    fun onNoteChange(value: String) = _uiState.update { it.copy(note = value, errorMessage = null, saved = false) }

    fun save() = viewModelScope.launch {
        val state = _uiState.value
        val amountMinor = AmountFormatter.parseToMinorOrNull(state.amountText)
        if (state.title.isBlank() || amountMinor == null) {
            _uiState.update { it.copy(errorMessage = "Başlık ve tutar zorunlu.", saved = false) }
            return@launch
        }

        _uiState.update { it.copy(isSaving = true, errorMessage = null, saved = false) }
        val now = AppClock.nowMillis()
        val transaction = Transaction(
            id = AppClock.newId(),
            userId = null,
            title = state.title.trim(),
            amountMinor = amountMinor,
            type = state.type,
            category = state.category,
            transactionDate = state.date,
            note = state.note.takeIf { it.isNotBlank() },
            syncStatus = SyncStatus.PENDING_CREATE,
            createdAt = now,
            updatedAt = now
        )
        val result = addTransactionUseCase(transaction)
        _uiState.update {
            it.copy(
                isSaving = false,
                errorMessage = result.exceptionOrNull()?.message,
                saved = result.isSuccess
            )
        }
    }

    fun clearSaved() = _uiState.update { it.copy(saved = false) }
}
