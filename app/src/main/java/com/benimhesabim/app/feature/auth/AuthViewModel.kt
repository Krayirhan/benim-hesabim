package com.benimhesabim.app.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benimhesabim.app.domain.usecase.LoginUseCase
import com.benimhesabim.app.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun onEmailChange(value: String) = _uiState.update { it.copy(email = value, errorMessage = null, isSuccess = false) }
    fun onPasswordChange(value: String) = _uiState.update { it.copy(password = value, errorMessage = null, isSuccess = false) }
    fun onConfirmPasswordChange(value: String) = _uiState.update { it.copy(confirmPassword = value, errorMessage = null, isSuccess = false) }

    fun login() = viewModelScope.launch {
        val state = _uiState.value
        _uiState.update { it.copy(isLoading = true, errorMessage = null, isSuccess = false) }
        val result = loginUseCase(state.email.trim(), state.password)
        _uiState.update {
            it.copy(
                isLoading = false,
                errorMessage = result.exceptionOrNull()?.message,
                isSuccess = result.isSuccess
            )
        }
    }

    fun register() = viewModelScope.launch {
        val state = _uiState.value
        if (state.password != state.confirmPassword) {
            _uiState.update { it.copy(errorMessage = "Şifreler eşleşmiyor.") }
            return@launch
        }
        _uiState.update { it.copy(isLoading = true, errorMessage = null, isSuccess = false) }
        val result = registerUseCase(state.email.trim(), state.password)
        _uiState.update {
            it.copy(
                isLoading = false,
                errorMessage = result.exceptionOrNull()?.message,
                isSuccess = result.isSuccess
            )
        }
    }

    fun clearSuccess() = _uiState.update { it.copy(isSuccess = false) }
}
