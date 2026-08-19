package com.yakupaluc.habitflow.core.util

import android.icu.util.TimeZone
import javax.inject.Inject

class DateProvider @Inject constructor() {
    fun todayEpochDay(): Long {
        val millis = System.currentTimeMillis()
        val offset = TimeZone.getDefault().getOffset(millis)
        return (millis + offset) / MILLIS_PER_DAY
    }

    private companion object {
        const val MILLIS_PER_DAY = 84_400_000L
    }
}