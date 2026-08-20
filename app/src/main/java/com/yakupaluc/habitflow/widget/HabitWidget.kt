package com.yakupaluc.habitflow.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.color.ColorProvider
import com.yakupaluc.habitflow.MainActivity
import com.yakupaluc.habitflow.domain.model.Habit
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.first

class HabitWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val entryPoint = EntryPointAccessors.fromApplication(
            context.applicationContext,
            WidgetEntryPoint::class.java
        )
        val habits = entryPoint.habitRepository()
            .observeActiveHabits()
            .first()

        provideContent {
            WidgetContent(habits)
        }
    }
}

@Composable
private fun WidgetContent(habits: List<Habit>) {
    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(Color(0xFF00696E))
            .padding(12.dp)
            .clickable(actionStartActivity<MainActivity>())
    ) {
        Text(
            text = "Today's habits",
            style = TextStyle(
                color = ColorProvider(day = Color.White, night = Color.White),
                fontWeight = FontWeight.Bold
            )
        )
        if (habits.isEmpty()) {
            Text(
                text = "No habits yet",
                style = TextStyle(color = ColorProvider(day = Color.White, night = Color.White))
            )
        } else {
            habits.take(5).forEach { habit ->
                val prefix = if (habit.isCompletedToday) "[X] " else "[ ] "
                Text(
                    text = prefix + habit.name,
                    maxLines = 1,
                    style = TextStyle(color = ColorProvider(day = Color.White, night = Color.White)),
                    modifier = GlanceModifier.fillMaxWidth().padding(vertical = 2.dp)
                )
            }
        }
    }
}