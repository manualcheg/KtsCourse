package com.manualcheg.ktscourse

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import ktscourse.composeapp.generated.resources.Res
import ktscourse.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource

@Serializable
sealed class Screen
@Serializable
object Onboard: Screen()

@Serializable
object Login: Screen()

@Composable
@Preview
fun App() {
    val navController = rememberNavController()

    @Composable
    fun OnboardScreen() {
        MaterialTheme {
            Column(
                modifier = Modifier
                    .safeContentPadding()
                    .fillMaxSize(),
            ) {
                Text(text = "Hello!")
                Image(painterResource(Res.drawable.compose_multiplatform), null)
                Button(onClick = { navController.navigate(Login) }) {
                    Text("Next screen")
                }
            }
        }
    }

    @Composable
    fun LoginScreen() {
        MaterialTheme {
            Column(
                modifier = Modifier
                    .safeContentPadding()
                    .fillMaxSize(),
            ) {
                Text(text = "Hello!")
                Image(painterResource(Res.drawable.compose_multiplatform), null)
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Onboard
    )
    {
        composable<Onboard> { OnboardScreen() }
        composable<Login> { LoginScreen() }
    }


    /*var showContent by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .safeContentPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Hello!")

        Button(onClick = { showContent = !showContent }) {
            Text("Click me!")
        }
        AnimatedVisibility(showContent) {
            val greeting = remember { Greeting().greet() }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(painterResource(Res.drawable.compose_multiplatform), null)
                Text("Compose: $greeting")
            }
        }
    }*/
}