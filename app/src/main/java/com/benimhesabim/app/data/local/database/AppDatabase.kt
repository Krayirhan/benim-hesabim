package com.benimhesabim.app.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.benimhesabim.app.data.local.dao.TransactionDao
import com.benimhesabim.app.data.local.entity.TransactionEntity

@Database(
    entities = [TransactionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}
