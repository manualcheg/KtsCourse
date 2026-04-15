package com.manualcheg.ktscourse.screenRocketLaunches.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.manualcheg.ktscourse.screenMain.presentation.components.LaunchList
import com.manualcheg.ktscourse.screenMain.presentation.components.PagedListContainer
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Suppress("ParamsComparedByRef")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RocketLaunchesScreen(
    rocketId: String,
    rocketName: String,
    onBackClick: () -> Unit,
    openLaunchDetails: (String) -> Unit,
    viewModel: RocketLaunchesViewModel = koinViewModel { parametersOf(rocketId) }
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(rocketName) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            if (uiState.isFromCache && !uiState.isLoading && uiState.error == null) {
                com.manualcheg.ktscourse.common.components.OfflineBadge()
            }

            PagedListContainer(
                isRefreshing = uiState.isRefreshing,
                onRefresh = viewModel::refresh,
                showLoading = uiState.showLoading,
                showErrorState = uiState.showErrorState,
                showEmptyState = uiState.showEmptyState,
                error = uiState.error,
                onRetry = viewModel::refresh,
            ) {
                LaunchList(
                    uiState = uiState.launchesUiState,
                    loadNextPage = {},
                    openLaunchDetails = openLaunchDetails,
                )
            }
        }
    }
}
