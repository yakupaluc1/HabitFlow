package com.yakupaluc.habitflow.domain.repository

import com.yakupaluc.habitflow.domain.model.Habit
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    fun observeActiveHabits(): Flow<List<Habit>>
    fun observeHabitById(id: String): Flow<Habit?>

    suspend fun upsertHabit(habit: Habit)
    suspend fun archiveHabit(id: String)
    suspend fun deleteHabit(habit: Habit)
    suspend fun setHabitCompleted(habitId: String, completed: Boolean)
}