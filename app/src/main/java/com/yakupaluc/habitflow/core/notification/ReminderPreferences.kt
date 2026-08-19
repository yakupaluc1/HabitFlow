package com.yakupaluc.habitflow.core.notification

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private  val Context.dataStore by preferencesDataStore(name = "reminder_settings")

data class ReminderSettings(
    val enabled: Boolean = false,
    val hour: Int = 20
)

class ReminderPreferences @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    val settings: Flow<ReminderSettings> =
        context.dataStore.data.map { prefs ->
            ReminderSettings(
                enabled = prefs[KEY_ENABLED] ?: false,
                hour = prefs[KEY_HOUR] ?: 20
            )
        }

    suspend fun setEnabled(enabled: Boolean) {
        context.dataStore.edit { it[KEY_ENABLED] = enabled }
    }

    private companion object {
        val KEY_ENABLED = booleanPreferencesKey("reminder_enabled")
        val KEY_HOUR = intPreferencesKey("reminder_hour")
    }
}