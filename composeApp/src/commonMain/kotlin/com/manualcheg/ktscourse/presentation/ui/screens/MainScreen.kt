package com.manualcheg.ktscourse.presentation.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.manualcheg.ktscourse.data.Launch
import com.manualcheg.ktscourse.presentation.ui.ViewModelMainScreen

@Composable
fun MainScreen(
    viewModelMainScreen: ViewModelMainScreen
) {
    val uiState by viewModelMainScreen.uiState.collectAsStateWithLifecycle()
    viewModelMainScreen.updateData()

    Scaffold { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ListOfFlights(uiState.listOfLaunches)
        }
    }
}

@Composable
fun ListOfFlights(list: List<Launch>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Launches",
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(start = 12.dp, top = 16.dp, bottom = 16.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))

        LazyColumn {
            items(list, key = { it.id }) {
                LaunchItem(it)
            }
        }
    }
}

@Composable
fun LaunchItem(launch: Launch) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            shadowElevation = 1.dp,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
                .padding(1.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = launch.name,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = launch.launchpad,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = launch.details,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = launch.launchDate,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}