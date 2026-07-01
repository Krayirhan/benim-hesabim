package com.benimhesabim.app.domain.repository

import com.benimhesabim.app.domain.model.AuthUser

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<AuthUser>
    suspend fun register(email: String, password: String): Result<AuthUser>
    suspend fun logout(): Result<Unit>
    suspend fun currentUser(): AuthUser?
}
