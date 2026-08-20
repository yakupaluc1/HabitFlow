package com.yakupaluc.habitflow.data.repository

import android.content.Context
import androidx.glance.appwidget.updateAll
import com.yakupaluc.habitflow.core.util.DateProvider
import com.yakupaluc.habitflow.data.local.dao.HabitDao
import com.yakupaluc.habitflow.data.local.entity.HabitCompletionEntity
import com.yakupaluc.habitflow.data.mapper.toDomain
import com.yakupaluc.habitflow.data.mapper.toEntity
import com.yakupaluc.habitflow.domain.model.Habit
import com.yakupaluc.habitflow.domain.repository.HabitRepository
import com.yakupaluc.habitflow.widget.HabitWidget
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HabitRepositoryImpl @Inject constructor(
    private val habitDao: HabitDao,
    private  val dateProvider: DateProvider,
    @param:ApplicationContext private val context: Context
) : HabitRepository{

    private suspend fun updateWidget() {
        HabitWidget().updateAll(context)
    }

    override fun observeActiveHabits(): Flow<List<Habit>> =
        habitDao.observeActiveHabits().map { list ->
            val today = dateProvider.todayEpochDay()
            list.map { it.toDomain(today) }
        }

    override fun observeHabitById(id: String): Flow<Habit?> =
        habitDao.observeHabitWithCompletions(id).map { list ->
            list.firstOrNull()?.toDomain(dateProvider.todayEpochDay())
        }

    override suspend fun upsertHabit(habit: Habit) {
        habitDao.upsertHabit(habit.toEntity())
        updateWidget()
    }


    override suspend fun archiveHabit(id: String) {
        habitDao.archiveHabit(id)
        updateWidget()
    }

    override suspend fun deleteHabit(habit: Habit) {
        habitDao.deleteHabit(habit.toEntity())
        updateWidget()
    }

    override suspend fun setHabitCompleted(habitId: String, completed: Boolean) {
        val today = dateProvider.todayEpochDay()
        if (completed){
            habitDao.upsertCompletion(
                HabitCompletionEntity(habitId = habitId, dateEpochDay = today)
            )
        } else {
            habitDao.deleteCompletion(habitId = habitId, epochDay = today)
        }
        updateWidget()
    }
}