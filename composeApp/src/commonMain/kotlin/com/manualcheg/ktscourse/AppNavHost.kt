package com.manualcheg.ktscourse

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.manualcheg.ktscourse.navigation.Screen
import com.manualcheg.ktscourse.presentation.ViewModelLoginUiScreen
import com.manualcheg.ktscourse.presentation.ui.ViewModelMainScreen
import com.manualcheg.ktscourse.presentation.ui.screens.LoginScreen
import com.manualcheg.ktscourse.presentation.ui.screens.MainScreen
import com.manualcheg.ktscourse.presentation.ui.screens.OnboardScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Onboard
    )
    {
        composable<Screen.Onboard> { OnboardScreen(navController) }
        composable<Screen.Login> {
            val viewModel: ViewModelLoginUiScreen = viewModel()
            LoginScreen(viewModel, navController)
        }
        composable<Screen.Main> {
            val viewModel: ViewModelMainScreen = viewModel()
            MainScreen(viewModel)
        }
    }
}
