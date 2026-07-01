package com.benimhesabim.app.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benimhesabim.app.domain.repository.AuthRepository
import com.benimhesabim.app.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val user = authRepository.currentUser()
            _uiState.update { it.copy(email = user?.email, isLoading = false) }
        }
    }

    fun logout() = viewModelScope.launch {
        _uiState.update { it.copy(isLoggingOut = true, errorMessage = null, loggedOut = false) }
        val result = logoutUseCase()
        _uiState.update {
            it.copy(
                isLoggingOut = false,
                errorMessage = result.exceptionOrNull()?.message,
                loggedOut = result.isSuccess
            )
        }
    }

    fun clearLoggedOut() = _uiState.update { it.copy(loggedOut = false) }
}
