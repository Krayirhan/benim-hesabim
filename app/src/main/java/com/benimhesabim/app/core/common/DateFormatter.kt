package com.benimhesabim.app.core.common

import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateFormatter {
    private val displayFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale("tr", "TR"))
    private val storageFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    fun display(date: LocalDate): String = date.format(displayFormatter)
    fun storage(date: LocalDate): String = date.format(storageFormatter)
    fun parse(storage: String): LocalDate = LocalDate.parse(storage, storageFormatter)
    fun monthBounds(yearMonth: YearMonth): Pair<String, String> {
        val first = yearMonth.atDay(1)
        val last = yearMonth.atEndOfMonth()
        return storage(first) to storage(last)
    }
}
