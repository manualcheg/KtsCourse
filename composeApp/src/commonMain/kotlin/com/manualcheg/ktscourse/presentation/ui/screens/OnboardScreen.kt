package com.manualcheg.ktscourse.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.manualcheg.ktscourse.navigation.Screen
import com.manualcheg.ktscourse.presentation.LocalDimensions
import ktscourse.composeapp.generated.resources.Res
import ktscourse.composeapp.generated.resources.noInternet
import org.jetbrains.compose.resources.painterResource

@Composable
fun OnboardScreen(navController: NavController) {
    val dimensions = LocalDimensions.current
    MaterialTheme {
        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "SpaceX",
                fontSize = dimensions.textSizeLarge,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(vertical = dimensions.paddingStandard)
                    .weight(1f)
            )
            AsyncImage(
                model = "https://art.pixilart.com/f4e56bb7d6.png",
                contentDescription = "space invider",
                placeholder = painterResource(Res.drawable.noInternet),
                error = painterResource(Res.drawable.noInternet),
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(30))
            )
            Button(
                onClick = { navController.navigate(Screen.Login) },
                modifier = Modifier
                    .weight(1f)
                    .padding(dimensions.paddingSmall)
                    .wrapContentSize()
            ) {
                Text("Next screen")
            }
        }
    }
}
