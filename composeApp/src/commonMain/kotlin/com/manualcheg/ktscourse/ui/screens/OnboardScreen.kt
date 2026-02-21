package com.manualcheg.ktscourse.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.manualcheg.ktscourse.navigation.Screen
import ktscourse.composeapp.generated.resources.Res
import ktscourse.composeapp.generated.resources.noInternet
import org.jetbrains.compose.resources.painterResource

@Composable
fun OnboardScreen(navController: NavController) {
    MaterialTheme {
        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Hello!",
                fontSize = 24.sp,
                modifier = Modifier.padding(40.dp)
            )
            Image(
                painter = rememberAsyncImagePainter(
                    model = "https://art.pixilart.com/f4e56bb7d6.png",
                    placeholder = painterResource(Res.drawable.noInternet),
                    error = painterResource(Res.drawable.noInternet),
                ),
                contentDescription = "space invider"
            )
            Button(
                onClick = { navController.navigate(Screen.Login) },
                modifier = Modifier.padding(40.dp)
            ) {
                Text("Next screen")
            }
        }
    }
}