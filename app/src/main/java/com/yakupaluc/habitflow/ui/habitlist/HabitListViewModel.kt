package com.yakupaluc.habitflow.ui.habitlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yakupaluc.habitflow.core.notification.ReminderScheduler
import com.yakupaluc.habitflow.domain.model.Habit
import com.yakupaluc.habitflow.domain.repository.HabitRepository
import com.yakupaluc.habitflow.domain.usecase.CalculateStreakUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class HabitListViewModel @Inject constructor(
    private val repository: HabitRepository,
    private val calculateStreak: CalculateStreakUseCase,
    private val reminderScheduler: ReminderScheduler
) : ViewModel() {

    val uiState: StateFlow<HabitListUiState> =
        repository.observeActiveHabits()
            .map { habits ->
                HabitListUiState(
                    items = habits.map { habit ->
                        HabitListItemUi(
                            habit = habit,
                            streak = calculateStreak(habit.completedDates)
                        )
                    },
                    isLoading = false
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HabitListUiState(isLoading = true)
            )

    fun addHabit() {
        viewModelScope.launch {
            val names =
                listOf("Drink water", "Read 10 pages", "Meditate", "Walk 5k steps", "Stretch")
            val colors = listOf("#EF5350", "#AB47BC", "#42A5F5", "#26A69A", "#FFA726")
            repository.upsertHabit(
                Habit(
                    id = UUID.randomUUID().toString(),
                    name = names.random(),
                    colorHex = colors.random(),
                    createdAt = System.currentTimeMillis(),
                    isArchived = false
                )
            )
        }
    }

    fun toggleCompletion(habit: Habit) {
        viewModelScope.launch {
            repository.setHabitCompleted(
                habitId = habit.id,
                completed = !habit.isCompletedToday
            )
        }
    }

    fun archiveHabit(habit: Habit) {
        viewModelScope.launch {
            repository.archiveHabit(habit.id)
        }
    }

    fun sendTestReminder() {
        reminderScheduler.sendTestReminderNow()
    }
}