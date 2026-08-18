package com.yakupaluc.habitflow.ui.habitlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yakupaluc.habitflow.domain.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HabitListViewModel @Inject constructor(
    repository: HabitRepository
) : ViewModel() {

    val uiState: StateFlow<HabitListUiState> =
        repository.observeActiveHabits()
            .map { habits ->
                HabitListUiState(habits = habits, isLoading = false)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HabitListUiState(isLoading = true)
            )
}