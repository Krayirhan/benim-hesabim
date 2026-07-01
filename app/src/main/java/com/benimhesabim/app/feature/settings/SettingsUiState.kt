package com.benimhesabim.app.feature.settings

data class SettingsUiState(
    val email: String? = null,
    val isLoading: Boolean = true,
    val isLoggingOut: Boolean = false,
    val errorMessage: String? = null,
    val loggedOut: Boolean = false
)
