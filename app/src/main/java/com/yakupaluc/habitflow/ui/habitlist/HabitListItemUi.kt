package com.yakupaluc.habitflow.ui.habitlist

import com.yakupaluc.habitflow.domain.model.Habit

data class HabitListItemUi(
    val habit: Habit,
    val streak: Int
)
