package com.benimhesabim.app.domain.model

import java.time.LocalDate

data class Transaction(
    val id: String,
    val userId: String?,
    val title: String,
    val amountMinor: Long,
    val type: TransactionType,
    val category: String,
    val transactionDate: LocalDate,
    val note: String?,
    val syncStatus: SyncStatus,
    val createdAt: Long,
    val updatedAt: Long
)
