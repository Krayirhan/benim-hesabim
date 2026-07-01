package com.benimhesabim.app.data.repository

import com.benimhesabim.app.data.remote.supabase.SupabaseRemoteDataSource
import com.benimhesabim.app.domain.model.AuthUser
import com.benimhesabim.app.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: SupabaseRemoteDataSource
) : AuthRepository {
    override suspend fun login(email: String, password: String): Result<AuthUser> = remoteDataSource.login(email, password)

    override suspend fun register(email: String, password: String): Result<AuthUser> = remoteDataSource.register(email, password)

    override suspend fun logout(): Result<Unit> = remoteDataSource.logout()

    override suspend fun currentUser(): AuthUser? = remoteDataSource.currentUser()
}
