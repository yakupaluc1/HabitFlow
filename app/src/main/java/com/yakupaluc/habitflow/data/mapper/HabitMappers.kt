package com.yakupaluc.habitflow.data.mapper

import com.yakupaluc.habitflow.data.local.entity.HabitEntity
import com.yakupaluc.habitflow.data.local.entity.HabitWithCompletions
import com.yakupaluc.habitflow.domain.model.Habit

fun HabitEntity.toDomain(): Habit = Habit(
    id = id,
    name = name,
    colorHex = colorHex,
    createdAt = createdAt,
    isArchived = isArchived
)

fun Habit.toEntity(): HabitEntity = HabitEntity(
    id = id,
    name = name,
    colorHex = colorHex,
    createdAt = createdAt,
    isArchived = isArchived,
    description = description
)

fun HabitWithCompletions.toDomain(todayEpochDay: Long): Habit = Habit(
    id = habit.id,
    name = habit.name,
    colorHex = habit.colorHex,
    createdAt = habit.createdAt,
    isArchived = habit.isArchived,
    description = habit.description,
    isCompletedToday = completions.any { it.dateEpochDay == todayEpochDay },
    completedDates = completions.map { it.dateEpochDay }.toSet()
)