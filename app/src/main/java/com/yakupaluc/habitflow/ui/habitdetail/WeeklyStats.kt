package com.yakupaluc.habitflow.ui.habitdetail

data class WeekBar(
    val weekIndex: Int,
    val completedCount: Int
)

fun buildWeeklyStats(
    completedDates: Set<Long>,
    todayEpochDay: Long,
    weekCount: Int = 7
): List<WeekBar> {
    val daysPerWeek = 7
    val totalDays = weekCount * daysPerWeek
    val startDay = todayEpochDay - (totalDays - 1)

    return (0 until weekCount).map { week ->
        val weekStart = startDay + week * daysPerWeek
        val weekEnd = weekStart + daysPerWeek - 1
        val count = completedDates.count() { it in weekStart..weekEnd }
        WeekBar(
            weekIndex = week,
            completedCount = count
        )
    }
}