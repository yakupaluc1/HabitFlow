package com.yakupaluc.habitflow.ui.habitdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.yakupaluc.habitflow.core.util.DateProvider
import com.yakupaluc.habitflow.domain.repository.HabitRepository
import com.yakupaluc.habitflow.ui.navigation.HabitDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitDetailViewModel @Inject constructor(
    private val repository: HabitRepository,
    private val dateProvider: DateProvider,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val route: HabitDetailRoute = savedStateHandle.toRoute()

    val uiState: StateFlow<HabitDetailUiState> =
        repository.observeHabitById(route.habitId)
            .map { habit ->
                HabitDetailUiState(
                    habit = habit,
                    todayEpochDay = dateProvider.todayEpochDay(),
                    isLoading = false
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HabitDetailUiState(isLoading = true)
            )

    fun deleteHabit(onDeleted: () -> Unit) {
        val habit = uiState.value.habit ?: return
        viewModelScope.launch {
            repository.deleteHabit(habit)
            onDeleted()
        }
    }
}