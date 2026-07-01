package com.benimhesabim.app.data.remote.supabase

import com.benimhesabim.app.data.remote.dto.TransactionDto
import com.benimhesabim.app.domain.model.AuthUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SupabaseRemoteDataSourceImpl @Inject constructor(
    private val provider: SupabaseClientProvider,
    private val sessionStore: AuthSessionStore
) : SupabaseRemoteDataSource {
    override suspend fun login(email: String, password: String): Result<AuthUser> = runCatching {
        check(provider.isConfigured) { "Supabase ayarları eksik." }
        val user = AuthUser(id = email, email = email)
        sessionStore.save(user)
        user
    }

    override suspend fun register(email: String, password: String): Result<AuthUser> = runCatching {
        check(provider.isConfigured) { "Supabase ayarları eksik." }
        val user = AuthUser(id = email, email = email)
        sessionStore.save(user)
        user
    }

    override suspend fun logout(): Result<Unit> = runCatching {
        sessionStore.clear()
        Unit
    }

    override suspend fun currentUser(): AuthUser? {
        return sessionStore.read()
    }

    override suspend fun upsertTransaction(transaction: TransactionDto): Result<Unit> = runCatching {
        Unit
    }

    override suspend fun deleteTransaction(transactionId: String): Result<Unit> = runCatching {
        Unit
    }
}
