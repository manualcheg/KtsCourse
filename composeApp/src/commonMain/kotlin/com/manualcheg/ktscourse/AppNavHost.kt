package com.manualcheg.ktscourse

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.manualcheg.ktscourse.navigation.Screen
import com.manualcheg.ktscourse.presentation.ui.screens.LoginScreen
import com.manualcheg.ktscourse.presentation.ui.screens.MainScreen
import com.manualcheg.ktscourse.presentation.ui.screens.onboarding.Onboarding

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Onboard
    )
    {
        composable<Screen.Onboard> {
            Onboarding {
                navController.navigate(Screen.Login) {
                    popUpTo(Screen.Onboard)
                }
            }
        }
        composable<Screen.Login> {
            LoginScreen({
                navController.navigate(Screen.Main) {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                }
            })
        }
        composable<Screen.Main> {
            MainScreen()
        }
    }
}
