package com.manualcheg.ktscourse.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.manualcheg.ktscourse.data.models.Launch
import com.manualcheg.ktscourse.presentation.LocalDimensions
import com.manualcheg.ktscourse.presentation.ui.screens.components.LaunchItem
import com.manualcheg.ktscourse.presentation.ui.screens.components.MainTopAppBar
import com.manualcheg.ktscourse.presentation.ui.screens.uistates.MainUiState
import com.manualcheg.ktscourse.presentation.viewmodels.ViewModelMainScreen
import ktscourse.composeapp.generated.resources.Res
import ktscourse.composeapp.generated.resources.main_screen_button_retry_text
import ktscourse.composeapp.generated.resources.main_screen_error_text
import ktscourse.composeapp.generated.resources.nothingFound
import ktscourse.composeapp.generated.resources.nothing_found_content_description
import ktscourse.composeapp.generated.resources.nothing_found_text
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onProfileClick: () -> Unit = {},
    viewModel: ViewModelMainScreen = viewModel { ViewModelMainScreen() }
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val isNextPageLoading by viewModel.isNextPageLoading.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val isLastPage by viewModel.isLastPageFlow.collectAsState()

    Scaffold(
        topBar = {
            MainTopAppBar(
                searchQuery = searchQuery,
                onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
                onProfileClick = onProfileClick
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = { viewModel.refresh() }
            ) {
                ShowListOfLaunches(
                    uiState,
                    searchQuery,
                    isNextPageLoading,
                    isLastPage = isLastPage,
                    { viewModel.updateData() },
                    { viewModel.loadNextPage() }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainScreen()
}

@Composable
fun ShowListOfLaunches(
    uiState: MainUiState,
    searchQuery: String,
    isNextPageLoading: Boolean,
    isLastPage: Boolean ,
    updateData: () -> Unit,
    loadNextPage: () -> Unit
) {
    val listState = rememberLazyListState()
    var lastScrolledQuery by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(uiState) {
        if (uiState is MainUiState.Success && searchQuery != lastScrolledQuery) {
            listState.scrollToItem(0)
            lastScrolledQuery = searchQuery
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is MainUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is MainUiState.Success -> {
                LaunchList(
                    uiState.launches,
                    listState,
                    isNextPageLoading,
                    isLastPage,
                    { loadNextPage() }
                )
            }

            is MainUiState.Error -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = stringResource(
                            Res.string.main_screen_error_text,
                            uiState.message
                        ),
                        color = MaterialTheme.colorScheme.error
                    )
                    Button(onClick = { updateData() }) {
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

@Preview
@Composable
fun PreviewListOfLaunches() {
        ShowListOfLaunches(
            MainUiState.Success(emptyList()),
            "",
            false,
            false,
            {},
            {}
        )
}

@Composable
fun LaunchList(
    launches: List<Launch>,
    listState: LazyListState,
    isNextPageLoading: Boolean,
    isLastPage: Boolean,
    loadNextPage: () -> Unit
) {
    val dimensions = LocalDimensions.current
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(dimensions.paddingSmall)
    ) {
        itemsIndexed(launches, key = { _, item -> item.id }) { index, launch ->
            LaunchItem(launch)
            if (index == launches.lastIndex) {
                loadNextPage()
            }
        }
        if (isNextPageLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensions.paddingMedium),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(dimensions.circularProgressIndicatorSize))
                }
            }
        }
        if (isLastPage && launches.isNotEmpty()) {
            item {
                Text(
                    text = "All launches loaded",
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
