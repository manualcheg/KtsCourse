package com.manualcheg.ktscourse.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.manualcheg.ktscourse.common.navigation.Screen
import com.manualcheg.ktscourse.common.repository.UserPreferencesRepository
import com.manualcheg.ktscourse.screenLogin.presentation.LoginScreen
import com.manualcheg.ktscourse.screenMain.presentation.MainScreen
import com.manualcheg.ktscourse.screenOnboarding.presentation.Onboarding
import com.manualcheg.ktscourse.screenProfile.presentation.ProfileScreen
import org.koin.compose.koinInject

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val userPreferencesRepository: UserPreferencesRepository = koinInject()
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
            Onboarding({
                navController.navigate(Screen.Login) {
                    popUpTo(Screen.Onboard)
                }
            })
        }
        composable<Screen.Login> {
            LoginScreen({
                navController.navigate(Screen.Main) {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                }
            })
        }
        composable<Screen.Main> {
            MainScreen(onProfileClick = {
                navController.navigate(Screen.Profile)
            })
        }

        composable<Screen.Profile> {
            ProfileScreen({
                navController.navigate(Screen.Login) {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                }
            }, {
                navController.navigateUp()
            })
        }
    }
}
