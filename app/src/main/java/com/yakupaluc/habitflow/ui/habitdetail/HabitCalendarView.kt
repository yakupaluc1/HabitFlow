package com.yakupaluc.habitflow.ui.habitdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HabitCalendarView(
    days: List<CalendarDay>,
    habitColor: Color,
    modifier: Modifier = Modifier
) {
    val emptyColor = MaterialTheme.colorScheme.surfaceVariant

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(days, key = { it.epochDay }) { day ->
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(4.dp))
                    .background(if (day.isCompleted) habitColor else emptyColor)
            )
        }
    }
}