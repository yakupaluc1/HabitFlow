package com.yakupaluc.habitflow.data.mapper

import com.yakupaluc.habitflow.data.local.entity.HabitEntity
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
    isArchived = isArchived
)