package com.yakupaluc.habitflow.core.notification

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import javax.inject.Inject

class ReminderScheduler @Inject constructor(
    private val workManager: WorkManager
) {
    fun sendTestReminderNow() {
        val request = OneTimeWorkRequestBuilder<HabitReminderWorker>().build()
        workManager.enqueue(request)
    }
}