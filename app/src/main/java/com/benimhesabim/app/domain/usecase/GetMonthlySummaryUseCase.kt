package com.benimhesabim.app.domain.usecase

import com.benimhesabim.app.domain.repository.TransactionRepository
import java.time.YearMonth
import javax.inject.Inject

class GetMonthlySummaryUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(yearMonth: YearMonth = YearMonth.now()) = repository.observeMonthlySummary(yearMonth)
}
