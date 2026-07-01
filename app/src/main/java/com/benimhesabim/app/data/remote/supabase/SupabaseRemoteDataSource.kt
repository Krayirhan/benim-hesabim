package com.benimhesabim.app.data.remote.supabase

import com.benimhesabim.app.data.remote.dto.TransactionDto
import com.benimhesabim.app.domain.model.AuthUser

interface SupabaseRemoteDataSource {
    suspend fun login(email: String, password: String): Result<AuthUser>
    suspend fun register(email: String, password: String): Result<AuthUser>
    suspend fun logout(): Result<Unit>
    suspend fun currentUser(): AuthUser?
    suspend fun upsertTransaction(transaction: TransactionDto): Result<Unit>
    suspend fun deleteTransaction(transactionId: String): Result<Unit>
}
