package com.yakupaluc.habitflow.ui.habitdetail

import com.yakupaluc.habitflow.domain.model.Habit

data class HabitDetailUiState(
    val habit: Habit? = null,
    val isLoading: Boolean = true
)