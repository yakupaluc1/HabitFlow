package com.yakupaluc.habitflow.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "habit_completions",
    primaryKeys = ["habit_id", "date_epoch_day"],
    foreignKeys = [
        ForeignKey(
            entity = HabitEntity::class,
            parentColumns = ["id"],
            childColumns = ["habit_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("habit_id")]
)
data class HabitCompletionEntity(
    @ColumnInfo(name = "habit_id")
    val habitId: String,

    @ColumnInfo(name = "date_epoch_day")
    val dateEpochDay: Long
)