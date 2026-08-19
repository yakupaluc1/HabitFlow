package com.yakupaluc.habitflow.domain.model

data class Habit(
    val id: String,
    val name: String,
    val colorHex: String,
    val createdAt: Long,
    val isArchived: Boolean,
    val isCompletedToday: Boolean = false,
    val completedDates: Set<Long> = emptySet()
)
