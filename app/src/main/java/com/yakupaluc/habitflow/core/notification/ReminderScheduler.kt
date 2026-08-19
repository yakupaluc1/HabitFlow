package com.yakupaluc.habitflow.core.notification

import android.icu.util.Calendar
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ReminderScheduler @Inject constructor(
    private val workManager: WorkManager
) {
    fun sendTestReminderNow() {
        val request = OneTimeWorkRequestBuilder<HabitReminderWorker>().build()
        workManager.enqueue(request)
    }

    fun scheduleDailyReminder(hour: Int) {
        val request = PeriodicWorkRequestBuilder<HabitReminderWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(computeInitialDelayMillis(hour), TimeUnit.MILLISECONDS)
            .build()

        workManager.enqueueUniquePeriodicWork(
            DAILY_REMINDER_WORK,
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }

    fun cancelDailyReminder() {
        workManager.cancelUniqueWork(DAILY_REMINDER_WORK)
    }

    private fun computeInitialDelayMillis(hour: Int): Long {
        val now = Calendar.getInstance()
        val next = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (before(now)) add(Calendar.DAY_OF_MONTH, 1)
        }

        return next.timeInMillis - now.timeInMillis
    }

    private companion object {
        const val DAILY_REMINDER_WORK = "daily_reminder_work"
    }
}