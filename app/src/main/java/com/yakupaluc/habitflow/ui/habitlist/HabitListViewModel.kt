package com.yakupaluc.habitflow.ui.habitlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yakupaluc.habitflow.core.notification.ReminderPreferences
import com.yakupaluc.habitflow.core.notification.ReminderScheduler
import com.yakupaluc.habitflow.domain.model.Habit
import com.yakupaluc.habitflow.domain.repository.HabitRepository
import com.yakupaluc.habitflow.domain.usecase.CalculateStreakUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class HabitListViewModel @Inject constructor(
    private val repository: HabitRepository,
    private val calculateStreak: CalculateStreakUseCase,
    private val reminderScheduler: ReminderScheduler,
    private val reminderPreferences: ReminderPreferences
) : ViewModel() {

    val uiState: StateFlow<HabitListUiState> =
        combine(
            repository.observeActiveHabits(),
            reminderPreferences.settings
        ) { habits, reminder ->
            HabitListUiState(
                items = habits.map { habit ->
                    HabitListItemUi(
                        habit = habit,
                        streak = calculateStreak(habit.completedDates)
                    )
                },
                reminderEnabled = reminder.enabled,
                isLoading = false
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HabitListUiState(isLoading = true)
            )

    fun addHabit(name: String, colorHex: String) {
        viewModelScope.launch {
            repository.upsertHabit(
                Habit(
                    id = UUID.randomUUID().toString(),
                    name = name,
                    colorHex = colorHex,
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

    fun setReminderEnabled(enabled: Boolean) {
        viewModelScope.launch {
            reminderPreferences.setEnabled(enabled)
            if (enabled) {
                reminderScheduler.scheduleDailyReminder(20)
            } else {
                reminderScheduler.cancelDailyReminder()
            }
        }
    }
}