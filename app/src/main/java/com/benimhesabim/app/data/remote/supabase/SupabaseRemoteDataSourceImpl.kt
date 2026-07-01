package com.benimhesabim.app.data.remote.supabase

import com.benimhesabim.app.data.remote.dto.TransactionDto
import com.benimhesabim.app.domain.model.AuthUser
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Email
import io.github.jan.supabase.auth.signInWith
import io.github.jan.supabase.auth.signOut
import io.github.jan.supabase.auth.signUpWith
import io.github.jan.supabase.postgrest.from
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SupabaseRemoteDataSourceImpl @Inject constructor(
    private val provider: SupabaseClientProvider
) : SupabaseRemoteDataSource {
    override suspend fun login(email: String, password: String): Result<AuthUser> = runCatching {
        check(provider.isConfigured) { "Supabase ayarları eksik." }
        provider.client.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
        currentUser() ?: AuthUser(
            id = email,
            email = email
        )
    }

    override suspend fun register(email: String, password: String): Result<AuthUser> = runCatching {
        check(provider.isConfigured) { "Supabase ayarları eksik." }
        provider.client.auth.signUpWith(Email) {
            this.email = email
            this.password = password
        }
        currentUser() ?: AuthUser(
            id = email,
            email = email
        )
    }

    override suspend fun logout(): Result<Unit> = runCatching {
        if (provider.isConfigured) {
            provider.client.auth.signOut()
        }
    }

    override suspend fun currentUser(): AuthUser? {
        val session = runCatching { provider.client.auth.currentSessionOrNull() }.getOrNull()
        val user = session?.user ?: return null
        return AuthUser(
            id = user.id,
            email = user.email
        )
    }

    override suspend fun upsertTransaction(transaction: TransactionDto): Result<Unit> = runCatching {
        check(provider.isConfigured) { "Supabase ayarları eksik." }
        provider.client.from("transactions").upsert(transaction)
        Unit
    }

    override suspend fun deleteTransaction(transactionId: String): Result<Unit> = runCatching {
        check(provider.isConfigured) { "Supabase ayarları eksik." }
        provider.client.from("transactions").delete {
            filter {
                eq("id", transactionId)
            }
        }
        Unit
    }
}
