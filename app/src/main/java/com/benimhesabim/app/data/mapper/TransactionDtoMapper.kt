package com.benimhesabim.app.data.mapper

import com.benimhesabim.app.data.remote.dto.TransactionDto
import com.benimhesabim.app.domain.model.Transaction
import com.benimhesabim.app.domain.model.TransactionType

object TransactionDtoMapper {
    fun toDto(domain: Transaction): TransactionDto = TransactionDto(
        id = domain.id,
        user_id = domain.userId,
        title = domain.title,
        amount_minor = domain.amountMinor,
        type = domain.type.name,
        category = domain.category,
        transaction_date = domain.transactionDate.toString(),
        note = domain.note,
        created_at = domain.createdAt,
        updated_at = domain.updatedAt
    )

    fun toDomain(dto: TransactionDto): Transaction = Transaction(
        id = dto.id,
        userId = dto.user_id,
        title = dto.title,
        amountMinor = dto.amount_minor,
        type = TransactionType.valueOf(dto.type),
        category = dto.category,
        transactionDate = java.time.LocalDate.parse(dto.transaction_date),
        note = dto.note,
        syncStatus = com.benimhesabim.app.domain.model.SyncStatus.SYNCED,
        createdAt = dto.created_at,
        updatedAt = dto.updated_at
    )
}
