package com.benimhesabim.app.data.mapper

import com.benimhesabim.app.core.common.DateFormatter
import com.benimhesabim.app.data.local.entity.TransactionEntity
import com.benimhesabim.app.domain.model.SyncStatus
import com.benimhesabim.app.domain.model.Transaction
import com.benimhesabim.app.domain.model.TransactionType

object TransactionMapper {
    fun toDomain(entity: TransactionEntity): Transaction = Transaction(
        id = entity.id,
        userId = entity.userId,
        title = entity.title,
        amountMinor = entity.amountMinor,
        type = TransactionType.valueOf(entity.type),
        category = entity.category,
        transactionDate = DateFormatter.parse(entity.transactionDate),
        note = entity.note,
        syncStatus = SyncStatus.valueOf(entity.syncStatus),
        createdAt = entity.createdAt,
        updatedAt = entity.updatedAt
    )

    fun toEntity(domain: Transaction): TransactionEntity = TransactionEntity(
        id = domain.id,
        userId = domain.userId,
        title = domain.title,
        amountMinor = domain.amountMinor,
        type = domain.type.name,
        category = domain.category,
        transactionDate = DateFormatter.storage(domain.transactionDate),
        note = domain.note,
        syncStatus = domain.syncStatus.name,
        createdAt = domain.createdAt,
        updatedAt = domain.updatedAt
    )
}
