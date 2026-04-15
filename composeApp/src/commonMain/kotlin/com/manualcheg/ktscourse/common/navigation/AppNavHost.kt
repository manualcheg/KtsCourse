package com.manualcheg.ktscourse.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalUriHandler
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.manualcheg.ktscourse.common.openSafeUri
import com.manualcheg.ktscourse.common.repository.UserPreferencesRepository
import com.manualcheg.ktscourse.common.util.NameHelper
import com.manualcheg.ktscourse.screenAbout.presentation.AboutScreen
import com.manualcheg.ktscourse.screenHistory.presentation.HistoryScreen
import com.manualcheg.ktscourse.screenLaunchDetails.presentation.LaunchDetailsScreen
import com.manualcheg.ktscourse.screenLogin.presentation.LoginScreen
import com.manualcheg.ktscourse.screenMain.presentation.MainScreen
import com.manualcheg.ktscourse.screenOnboarding.presentation.Onboarding
import com.manualcheg.ktscourse.screenProfile.presentation.ProfileScreen
import com.manualcheg.ktscourse.screenRocketDetails.presentation.RocketDetailsScreen
import com.manualcheg.ktscourse.screenRocketLaunches.presentation.RocketLaunchesScreen
import com.manualcheg.ktscourse.screenSettings.presentation.SettingsScreen
import com.manualcheg.ktscourse.screenStatistics.presentation.StatisticsScreen
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
                    navController.navigate(Screen.Settings)
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

        composable<Screen.LaunchDetails>(
            deepLinks = listOf(
                navDeepLink<Screen.LaunchDetails>(
                    basePath = "spacex://launch",
                ),
            ),
        ) { backstackEntry ->
            val launchDetails: Screen.LaunchDetails = backstackEntry.toRoute()
            LaunchDetailsScreen(
                onBackClick = { navController.navigateUp() },
                launchId = launchDetails.id,
                onRocketClick = { navController.navigate(Screen.RocketDetails(it)) },
                openLink = { url ->
                    uriHandler.openSafeUri(url)
                },
                nameHelper = koinInject<NameHelper>(),
            )
        }

        composable<Screen.RocketDetails>(
            deepLinks = listOf(
                navDeepLink<Screen.RocketDetails>(
                    basePath = "spacex://rocket",
                ),
            ),
        ) {
            val rocketDetails: Screen.RocketDetails = it.toRoute()
            RocketDetailsScreen(
                onBackClick = { navController.navigateUp() },
                rocketId = rocketDetails.id,
                onLinkClick = { url ->
                    uriHandler.openSafeUri(url)
                },
                onViewLaunchesClick = { rocketId, rocketName ->
                    navController.navigate(Screen.RocketLaunches(rocketId, rocketName))
                },
            )
        }

        composable<Screen.RocketLaunches> {
            val rocketLaunches: Screen.RocketLaunches = it.toRoute()
            RocketLaunchesScreen(
                rocketId = rocketLaunches.rocketId,
                rocketName = rocketLaunches.rocketName,
                onBackClick = { navController.navigateUp() },
                openLaunchDetails = { id ->
                    navController.navigate(Screen.LaunchDetails(id))
                },
            )
        }

        composable<Screen.Settings> {
            SettingsScreen(
                onBackClick = { navController.navigateUp() },
                onProfileClick = { navController.navigate(Screen.Profile) },
                onCompanyHistoryClick = { navController.navigate(Screen.History) },
                onStatisticsClick = { navController.navigate(Screen.Statistic) },
                onAboutSpaceXClick = { navController.navigate(Screen.AboutCompany) },
            )
        }

        composable<Screen.AboutCompany> {
            AboutScreen(onNavigateUp = { navController.navigateUp() })
        }

        composable<Screen.History> {
            HistoryScreen(onNavigateUp = { navController.navigateUp() })
        }

        composable<Screen.Statistic> {
            StatisticsScreen(onNavigateUp = { navController.navigateUp() })
        }
    }
}
