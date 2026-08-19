package com.yakupaluc.habitflow.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object HabitListRoute
@Serializable
data class HabitDetailRoute(val habitId: String)