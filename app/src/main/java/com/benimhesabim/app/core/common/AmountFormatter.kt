package com.benimhesabim.app.core.common

import java.text.NumberFormat
import java.util.Locale

object AmountFormatter {
    private val formatter: NumberFormat = NumberFormat.getNumberInstance(Locale("tr", "TR"))

    init {
        formatter.minimumFractionDigits = 2
        formatter.maximumFractionDigits = 2
    }

    fun formatMinor(amountMinor: Long): String = "${formatter.format(java.math.BigDecimal(amountMinor).movePointLeft(2))} TL"

    fun parseToMinorOrNull(input: String): Long? {
        val normalized = input.trim().replace(".", "").replace(",", ".")
        val value = normalized.toBigDecimalOrNull() ?: return null
        return value.multiply(java.math.BigDecimal(100)).longValueExact()
    }
}
