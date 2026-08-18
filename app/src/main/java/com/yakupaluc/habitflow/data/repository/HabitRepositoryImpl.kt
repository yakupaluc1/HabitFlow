package com.yakupaluc.habitflow.data.repository

import com.yakupaluc.habitflow.data.local.dao.HabitDao
import com.yakupaluc.habitflow.data.mapper.toDomain
import com.yakupaluc.habitflow.data.mapper.toEntity
import com.yakupaluc.habitflow.domain.model.Habit
import com.yakupaluc.habitflow.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HabitRepositoryImpl @Inject constructor(
    private val habitDao: HabitDao
) : HabitRepository{
    override fun observeActiveHabits(): Flow<List<Habit>> =
        habitDao.observeActiveHabits().map { entities ->
            entities.map { it.toDomain() }
        }

    override fun observeHabitById(id: String): Flow<Habit?> =
        habitDao.observeHabitById(id).map { entity ->
            entity?.toDomain()
        }

    override suspend fun upsertHabit(habit: Habit) =
        habitDao.upsertHabit(habit.toEntity())


    override suspend fun archiveHabit(id: String) =
        habitDao.archiveHabit(id)

    override suspend fun deleteHabit(habit: Habit) =
        habitDao.deleteHabit(habit.toEntity())
}