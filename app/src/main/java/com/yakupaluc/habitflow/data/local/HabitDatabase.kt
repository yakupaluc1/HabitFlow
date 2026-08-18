package com.yakupaluc.habitflow.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yakupaluc.habitflow.data.local.dao.HabitDao
import com.yakupaluc.habitflow.data.local.entity.HabitEntity

@Database(
    entities = [HabitEntity::class],
    version = 1,
    exportSchema = true
)
abstract class HabitDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}