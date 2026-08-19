package com.yakupaluc.habitflow.ui.habitdetail

data class CalendarDay(
    val epochDay: Long,
    val isCompleted: Boolean
)

fun buildCalendarDays(
    completedDays: Set<Long>,
    todayEpochDay: Long,
    totalDays: Int = 105
): List<CalendarDay> {
    val startDay = todayEpochDay - (totalDays - 1)
    return (startDay..todayEpochDay).map { day ->
        CalendarDay(
            epochDay = day,
            isCompleted = day in completedDays
        )
    }
}