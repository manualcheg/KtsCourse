package com.manualcheg.ktscourse

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.manualcheg.ktscourse.navigation.Login
import com.manualcheg.ktscourse.navigation.Onboard
import com.manualcheg.ktscourse.ui.screens.LoginScreen
import com.manualcheg.ktscourse.ui.screens.OnboardScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Onboard
    )
    {
        composable<Onboard> { OnboardScreen(navController) }
        composable<Login> { LoginScreen() }
    }
}