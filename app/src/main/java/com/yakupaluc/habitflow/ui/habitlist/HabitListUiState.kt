package com.yakupaluc.habitflow.ui.habitlist

import com.yakupaluc.habitflow.domain.model.Habit

data class HabitListUiState(
    val habits: List<Habit> = emptyList(),
    val isLoading: Boolean = true
)