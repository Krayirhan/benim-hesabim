package com.benimhesabim.app.feature.auth

sealed interface SplashUiState {
    data object Loading : SplashUiState
    data object Login : SplashUiState
    data object Home : SplashUiState
}
