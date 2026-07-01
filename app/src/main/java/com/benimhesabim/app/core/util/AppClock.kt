package com.benimhesabim.app.core.util

import java.util.UUID

object AppClock {
    fun nowMillis(): Long = System.currentTimeMillis()
    fun newId(): String = UUID.randomUUID().toString()
}
