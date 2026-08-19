package com.yakupaluc.habitflow.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yakupaluc.habitflow.data.local.dao.HabitDao
import com.yakupaluc.habitflow.data.local.entity.HabitCompletionEntity
import com.yakupaluc.habitflow.data.local.entity.HabitEntity

@Database(
    entities = [HabitEntity::class, HabitCompletionEntity::class],
    version = 3,
    exportSchema = true
)
abstract class HabitDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}