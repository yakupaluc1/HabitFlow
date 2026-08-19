package com.yakupaluc.habitflow.ui.habitlist

import com.yakupaluc.habitflow.domain.model.Habit

data class HabitListUiState(
    val items: List<HabitListItemUi> = emptyList(),
    val isLoading: Boolean = true
)