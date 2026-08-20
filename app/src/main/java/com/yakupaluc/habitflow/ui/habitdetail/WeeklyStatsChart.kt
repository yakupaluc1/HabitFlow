package com.yakupaluc.habitflow.ui.habitdetail

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp

@Composable
fun WeeklyStatsChar(
    bars: List<WeekBar>,
    barColor: Color,
    trackColor: Color,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(140.dp)
    ) {
        drawWeeklyBars(bars, barColor, trackColor)
    }
}

private fun DrawScope.drawWeeklyBars(
    bars: List<WeekBar>,
    barColor: Color,
    trackColor: Color
) {
    if (bars.isEmpty()) return

    val maxValue = 7
    val barCount = bars.size
    val gap = size.width * 0.03f
    val barWidth = (size.width - gap * (barCount - 1)) / barCount
    val cornerRadius = androidx.compose.ui.geometry.CornerRadius(barWidth * 0.2f)

    bars.forEachIndexed { index, bar ->
        val left = index * (barWidth + gap)
        val fraction = bar.completedCount.toFloat() / maxValue
        val barHeight = size.height * fraction

        drawRoundRect(
            color = trackColor,
            topLeft = androidx.compose.ui.geometry.Offset(left, 0f),
            size = androidx.compose.ui.geometry.Size(barWidth, size.height),
            cornerRadius = cornerRadius
        )

        if (bar.completedCount > 0) {
            drawRoundRect(
                color = barColor,
                topLeft = androidx.compose.ui.geometry.Offset(left, size.height - barHeight),
                size = androidx.compose.ui.geometry.Size(barWidth, barHeight),
                cornerRadius = cornerRadius
            )
        }
    }
}