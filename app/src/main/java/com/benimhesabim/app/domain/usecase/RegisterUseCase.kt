package com.benimhesabim.app.domain.usecase

import com.benimhesabim.app.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) = repository.register(email, password)
}
