package com.yakupaluc.habitflow.domain.usecase

import com.yakupaluc.habitflow.core.util.DateProvider
import junit.framework.Assert.assertEquals
import org.junit.Test

class CalculateStreakUseCaseTest {
    private class FakeDateProvider(private val today: Long) : DateProvider() {
        override fun todayEpochDay(): Long = today
    }

    private fun useCaseWithToday(today: Long) =
        CalculateStreakUseCase(FakeDateProvider(today))

    @Test
    fun `empty completions returns zero streak`() {
        val useCase = useCaseWithToday(today = 100)
        val streak = useCase(completedDates = emptySet())
        assertEquals(0, streak)
    }

    @Test
    fun `completed today only returns streak of one`() {
        val useCase = useCaseWithToday(today = 100)
        val streak = useCase(completedDates = setOf(100))
        assertEquals(1, streak)
    }

    @Test
    fun `consecutive days including today are counted`() {
        val useCase = useCaseWithToday(100)
        val streak = useCase(completedDates = setOf(98, 99, 100))
        assertEquals(3, streak)
    }

    @Test
    fun `streak counts from yesterday when today not yet done`() {
        val useCase = useCaseWithToday(today = 100)
        val streak = useCase(completedDates = setOf(98, 99))
        assertEquals(2, streak)
    }

    @Test
    fun `a gap breaks the streak`() {
        val useCase = useCaseWithToday(today = 100)
        val streak = useCase(completedDates = setOf(97, 98, 100))
        assertEquals(1, streak)
    }

    @Test
    fun `old completions with no recent day return zero`() {
        val useCase = useCaseWithToday(today = 100)
        val streak = useCase(completedDates = setOf(95, 96, 97))
        assertEquals(0, streak)
    }
}