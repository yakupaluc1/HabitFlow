package com.yakupaluc.habitflow.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.yakupaluc.habitflow.data.local.entity.HabitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits WHERE is_archived = 0 ORDER BY created_at DESC")
    fun observeActiveHabits(): Flow<List<HabitEntity>>

    @Query("SELECT * FROM habits WHERE id = :id")
    fun observeHabitById(id: String): Flow<HabitEntity>

    @Upsert
    suspend fun upsertHabit(habit: HabitEntity)

    @Query("UPDATE habits SET is_archived = 1 WHERE id = :id")
    suspend fun archiveHabit(id: String)

    @Delete
    suspend fun deleteHabit(habit: HabitEntity)
}