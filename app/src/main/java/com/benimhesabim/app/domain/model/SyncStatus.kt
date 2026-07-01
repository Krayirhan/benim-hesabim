package com.benimhesabim.app.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class SyncStatus {
    PENDING_CREATE,
    PENDING_UPDATE,
    PENDING_DELETE,
    SYNCED,
    FAILED
}
