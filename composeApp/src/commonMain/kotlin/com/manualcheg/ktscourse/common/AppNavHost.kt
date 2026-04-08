package com.manualcheg.ktscourse.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalUriHandler
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.manualcheg.ktscourse.common.navigation.Screen
import com.manualcheg.ktscourse.common.repository.UserPreferencesRepository
import com.manualcheg.ktscourse.screenLaunchDetails.presentation.LaunchDetailsScreen
import com.manualcheg.ktscourse.screenLogin.presentation.LoginScreen
import com.manualcheg.ktscourse.screenMain.presentation.MainScreen
import com.manualcheg.ktscourse.screenOnboarding.presentation.Onboarding
import com.manualcheg.ktscourse.screenProfile.presentation.ProfileScreen
import com.manualcheg.ktscourse.screenRocketDetails.presentation.RocketDetailsScreen
import org.koin.compose.koinInject

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val userPreferencesRepository: UserPreferencesRepository = koinInject()
    val userData by userPreferencesRepository.userData.collectAsState(initial = null)
    val uriHandler = LocalUriHandler.current

    val user = userData ?: return

    val startScreen = when {
        user.isLoggedIn -> Screen.Main
        user.firstStart -> Screen.Onboard
        else -> Screen.Login
    }

    NavHost(
        navController = navController,
        startDestination = startScreen,
    )
    {
        composable<Screen.Onboard> {
            Onboarding(
                {
                    navController.navigate(Screen.Login) {
                        popUpTo(Screen.Onboard)
                    }
                },
            )
        }
        composable<Screen.Login> {
            LoginScreen(
                {
                    navController.navigate(Screen.Main) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                },
            )
        }
        composable<Screen.Main> {
            MainScreen(
                onProfileClick = {
                    navController.navigate(Screen.Profile)
                },
                openLaunchDetails = { id ->
                    navController.navigate(Screen.LaunchDetails(id))
                },
                openRocketDetails = { id ->
                    navController.navigate(Screen.RocketDetails(id))
                },
            )
        }

        composable<Screen.Profile> {
            ProfileScreen(
                {
                    navController.navigate(Screen.Login) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                },
                {
                    navController.navigateUp()
                },
            )
        }

        composable<Screen.LaunchDetails> { backstackEntry ->
            val launchDetails: Screen.LaunchDetails = backstackEntry.toRoute()
            LaunchDetailsScreen(
                onBackClick = { navController.navigateUp() },
                launchId = launchDetails.id,
                onRocketClick = { navController.navigate(Screen.RocketDetails(it)) },
                openLink = { url ->
                    uriHandler.openSafeUri(url)
                },
            )
        }

        composable<Screen.RocketDetails> {
            val rocketDetails: Screen.RocketDetails = it.toRoute()
            RocketDetailsScreen(
                onBackClick = { navController.navigateUp() },
                rocketId = rocketDetails.id,
                onLinkClick = { url ->
                    uriHandler.openSafeUri(url)
                },
            )
        }
    }
}
