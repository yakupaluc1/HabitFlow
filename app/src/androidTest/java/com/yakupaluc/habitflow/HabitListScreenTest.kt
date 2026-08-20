package com.yakupaluc.habitflow

import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HabitListScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun empty_state_isShown_forFreshDatabase() {
        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithText("No habits yet")
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithText("HabitFlow").assertIsDisplayed()
        composeRule.onNodeWithText("No habits yet").assertIsDisplayed()
    }

    @Test
    fun addingHabit_showsItInTheList() {
        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithText("No habits yet")
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithContentDescription("Add habit").performClick()

        composeRule.onNodeWithText("Habit name").performTextInput("Morning run")

        composeRule.onNodeWithText("Add").performClick()

        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithText("Morning run")
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithText("Morning run").assertIsDisplayed()
    }

    @Test
    fun completingHabit_showsStreakBadge() {
        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithText("No habits yet")
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithContentDescription("Add habit").performClick()
        composeRule.onNodeWithText("Habit name").performTextInput("Read")
        composeRule.onNodeWithText("Add").performClick()
        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithText("Read")
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNode(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Checkbox))
            .performClick()

        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithText("🔥 1")
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithText("🔥 1").assertIsDisplayed()
    }

    @Test
    fun tappingHabit_opensDetailScreen() {
        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithText("No habits yet")
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithContentDescription("Add habit").performClick()
        composeRule.onNodeWithText("Habit name").performTextInput("Meditate")
        composeRule.onNodeWithText("Add").performClick()
        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithText("Meditate")
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithText("Meditate").performClick()

        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithText("Last 15 weeks")
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithText("Last 15 weeks").assertIsDisplayed()
    }
}