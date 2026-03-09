package com.manualcheg.ktscourse.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import coil3.compose.AsyncImage
import com.manualcheg.ktscourse.data.models.Launch
import com.manualcheg.ktscourse.data.models.LaunchStatus
import com.manualcheg.ktscourse.presentation.LocalDimensions
import com.manualcheg.ktscourse.presentation.ui.screens.UIStates.MainUiState
import com.manualcheg.ktscourse.presentation.viewmodels.ViewModelMainScreen
import ktscourse.composeapp.generated.resources.Res
import ktscourse.composeapp.generated.resources.main_screen_button_retry_text
import ktscourse.composeapp.generated.resources.main_screen_error_text
import ktscourse.composeapp.generated.resources.main_screen_main_text_launches
import ktscourse.composeapp.generated.resources.main_screen_mission_image_cont_descript_launch
import ktscourse.composeapp.generated.resources.nothingFound
import ktscourse.composeapp.generated.resources.nothing_found_content_description
import ktscourse.composeapp.generated.resources.nothing_found_text
import ktscourse.composeapp.generated.resources.rocket_launch_128
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModelMainScreen: ViewModelMainScreen
) {
    val dimensions = LocalDimensions.current
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    stringResource(Res.string.main_screen_main_text_launches),
                    fontSize = dimensions.textSizeSmall,
                )
            })
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ShowListOfLaunches(viewModelMainScreen)
        }
    }
}

@Composable
fun ShowListOfLaunches(viewModelMainScreen: ViewModelMainScreen) {
    val uiState = viewModelMainScreen.uiState

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is MainUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is MainUiState.Success -> {
                LaunchList(uiState.launches)
            }

            is MainUiState.Error -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = stringResource(Res.string.main_screen_error_text, uiState.message),
                        color = MaterialTheme.colorScheme.error
                    )
                    Button(onClick = { viewModelMainScreen.updateData() }) {
                        Text(stringResource(Res.string.main_screen_button_retry_text))
                    }
                }
            }

            is MainUiState.Empty -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        imageResource(Res.drawable.nothingFound),
                        contentDescription = stringResource(Res.string.nothing_found_content_description)
                    )
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = stringResource(Res.string.nothing_found_text),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
fun LaunchList(launches: List<Launch>) {
    val dimensions = LocalDimensions.current
    Column(modifier = Modifier.fillMaxWidth()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(dimensions.paddingSmall)
        ) {
            items(launches, key = { it.id }) {
                LaunchItem(it)
            }
        }
    }
}

@Composable
fun LaunchItem(launch: Launch) {
    val dimensions = LocalDimensions.current
    val statusColor = when (launch.status) {
        LaunchStatus.SUCCESS -> Color(0xFF4CAF50)
        LaunchStatus.FAILURE -> MaterialTheme.colorScheme.error
        LaunchStatus.UPCOMING -> Color(0xFF2196F3)
    }

    val statusText = when (launch.status) {
        LaunchStatus.SUCCESS -> "Success"
        LaunchStatus.FAILURE -> "Failed"
        LaunchStatus.UPCOMING -> "Upcoming"
    }

    Surface(
        shape = MaterialTheme.shapes.medium,
        shadowElevation = dimensions.shadowElevation,
        color = MaterialTheme.colorScheme.outline,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensions.paddingLarge)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensions.paddingLarge),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (launch.imageUrl != "") {
                AsyncImage(
                    model = launch.imageUrl,
                    contentDescription = stringResource(Res.string.main_screen_mission_image_cont_descript_launch),
                    placeholder = painterResource(Res.drawable.rocket_launch_128),
                    error = painterResource(Res.drawable.rocket_launch_128),
                    modifier = Modifier
                        .size(dimensions.imageSize)
                )
            }

            Spacer(modifier = Modifier.width(dimensions.paddingMedium))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Text(
                    text = launch.name,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(dimensions.spacerHeight))

                Text(
                    text = launch.flightNumber.toString(),
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleSmall
                )

                if (launch.details.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(dimensions.spacerHeight))
                    Text(
                        text = launch.details,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 2
                    )
                }

                Spacer(modifier = Modifier.height(dimensions.spacerHeight))

                Text(
                    text = launch.launchDate,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleSmall
                )

                Spacer(modifier = Modifier.height(dimensions.spacerHeight))

                Row {
                    Box(
                        modifier = Modifier
                            .size(
                                dimensions.successIndicatorSize
                            ).background(statusColor, shape = CircleShape)
                            .align(Alignment.CenterVertically)
                    )

                    Spacer(modifier = Modifier.width(dimensions.paddingSmall))

                    Text(text = statusText, fontSize = dimensions.textSizeSmallest)
                }
            }
        }
    }
}
