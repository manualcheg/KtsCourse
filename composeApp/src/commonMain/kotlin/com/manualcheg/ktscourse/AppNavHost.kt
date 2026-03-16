package com.manualcheg.ktscourse

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.manualcheg.ktscourse.data.local_storage.DataStorePreferencesProvider
import com.manualcheg.ktscourse.data.repository.UserPreferencesRepository
import com.manualcheg.ktscourse.navigation.Screen
import com.manualcheg.ktscourse.presentation.ui.screens.LoginScreen
import com.manualcheg.ktscourse.presentation.ui.screens.MainScreen
import com.manualcheg.ktscourse.presentation.ui.screens.onboarding.Onboarding

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val userPreferencesRepository = remember {
        UserPreferencesRepository(DataStorePreferencesProvider.datastore)
    }
    val userData by userPreferencesRepository.userData.collectAsState(initial = null)

    val user = userData ?: return

    val startScreen = when {
        user.isLoggedIn -> Screen.Main
        user.firstStart -> Screen.Onboard
        else -> Screen.Login
    }

    NavHost(
        navController = navController,
        startDestination = startScreen
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
