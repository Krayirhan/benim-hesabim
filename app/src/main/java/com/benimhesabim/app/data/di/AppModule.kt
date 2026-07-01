package com.benimhesabim.app.data.di

import android.content.Context
import androidx.room.Room
import com.benimhesabim.app.data.local.dao.TransactionDao
import com.benimhesabim.app.data.local.database.AppDatabase
import com.benimhesabim.app.data.repository.AuthRepositoryImpl
import com.benimhesabim.app.data.repository.TransactionRepositoryImpl
import com.benimhesabim.app.data.remote.supabase.SupabaseRemoteDataSource
import com.benimhesabim.app.data.remote.supabase.SupabaseRemoteDataSourceImpl
import com.benimhesabim.app.domain.repository.AuthRepository
import com.benimhesabim.app.domain.repository.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppBindingsModule {
    @Binds
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindTransactionRepository(impl: TransactionRepositoryImpl): TransactionRepository

    @Binds
    abstract fun bindRemoteDataSource(impl: SupabaseRemoteDataSourceImpl): SupabaseRemoteDataSource
}

@Module
@InstallIn(SingletonComponent::class)
object AppProvidesModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "benim_hesabim.db").build()

    @Provides
    fun provideTransactionDao(database: AppDatabase): TransactionDao = database.transactionDao()
}
