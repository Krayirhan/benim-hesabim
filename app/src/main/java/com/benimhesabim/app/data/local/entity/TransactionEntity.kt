package com.benimhesabim.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey val id: String,
    val userId: String?,
    val title: String,
    val amountMinor: Long,
    val type: String,
    val category: String,
    val transactionDate: String,
    val note: String?,
    val syncStatus: String,
    val createdAt: Long,
    val updatedAt: Long
)
