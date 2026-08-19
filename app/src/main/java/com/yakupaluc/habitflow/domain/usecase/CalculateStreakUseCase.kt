package com.yakupaluc.habitflow.domain.usecase

import com.yakupaluc.habitflow.core.util.DateProvider
import javax.inject.Inject

class CalculateStreakUseCase @Inject constructor(
    private val dateProvider: DateProvider
) {
    operator fun invoke(completedDates: Set<Long>): Int {
        if (completedDates.isEmpty()) return 0

        val today = dateProvider.todayEpochDay()
        var day = when {
            today in completedDates -> today
            (today - 1) in completedDates -> today - 1
            else -> return 0
        }

        var streak = 0
        while (day in completedDates) {
            streak++
            day--
        }
        return streak
    }
}