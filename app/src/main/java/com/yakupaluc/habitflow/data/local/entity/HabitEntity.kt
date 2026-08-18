package com.yakupaluc.habitflow.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "name")
    val name : String,

    @ColumnInfo(name = "color_hex")
    val colorHex: String,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "is_archived")
    val isArchived: Boolean = false
)