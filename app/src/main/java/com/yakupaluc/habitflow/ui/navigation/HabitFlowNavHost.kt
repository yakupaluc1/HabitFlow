package com.yakupaluc.habitflow.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yakupaluc.habitflow.ui.habitdetail.HabitDetailScreen
import com.yakupaluc.habitflow.ui.habitlist.HabitListScreen

@Composable
fun HabitFlowNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HabitListRoute
    ) {
        composable<HabitListRoute> {
            HabitListScreen(
                onHabitClick = { habitId ->
                    navController.navigate(HabitDetailRoute(habitId))
                }
            )
        }
        composable<HabitDetailRoute> {
            HabitDetailScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}