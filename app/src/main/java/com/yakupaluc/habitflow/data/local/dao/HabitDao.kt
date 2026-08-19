package com.yakupaluc.habitflow.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.yakupaluc.habitflow.data.local.entity.HabitCompletionEntity
import com.yakupaluc.habitflow.data.local.entity.HabitEntity
import com.yakupaluc.habitflow.data.local.entity.HabitWithCompletions
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Transaction
    @Query("SELECT * FROM habits WHERE is_archived = 0 ORDER BY created_at DESC")
    fun observeActiveHabits(): Flow<List<HabitWithCompletions>>

    @Query("SELECT * FROM habits WHERE id = :id")
    fun observeHabitById(id: String): Flow<HabitEntity?>

    @Upsert
    suspend fun upsertHabit(habit: HabitEntity)

    @Query("UPDATE habits SET is_archived = 1 WHERE id = :id")
    suspend fun archiveHabit(id: String)

    @Delete
    suspend fun deleteHabit(habit: HabitEntity)

    @Upsert
    suspend fun upsertCompletion(completion: HabitCompletionEntity)

    @Query("DELETE FROM habit_completions WHERE habit_id = :habitId AND date_epoch_day = :epochDay")
    suspend fun deleteCompletion(habitId: String, epochDay: Long)

    @Transaction
    @Query("SELECT * FROM habits WHERE id = :id")
    fun observeHabitWithCompletions(id: String): Flow<HabitWithCompletions>
}