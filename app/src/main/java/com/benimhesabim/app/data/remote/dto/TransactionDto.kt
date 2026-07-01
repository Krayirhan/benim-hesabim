package com.benimhesabim.app.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class TransactionDto(
    val id: String,
    val user_id: String?,
    val title: String,
    val amount_minor: Long,
    val type: String,
    val category: String,
    val transaction_date: String,
    val note: String?,
    val created_at: Long,
    val updated_at: Long
)
