package com.manualcheg.ktscourse.screenMain.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import com.manualcheg.ktscourse.common.LocalDimensions
import com.manualcheg.ktscourse.screenMain.domain.model.Launch
import com.manualcheg.ktscourse.screenMain.presentation.components.LaunchItem
import com.manualcheg.ktscourse.screenMain.presentation.components.MainTopAppBar
import ktscourse.composeapp.generated.resources.Res
import ktscourse.composeapp.generated.resources.main_screen_button_retry_text
import ktscourse.composeapp.generated.resources.main_screen_error_text
import ktscourse.composeapp.generated.resources.nothingFound
import ktscourse.composeapp.generated.resources.nothing_found_content_description
import ktscourse.composeapp.generated.resources.nothing_found_text
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onProfileClick: () -> Unit = {},
    viewModel: ViewModelMainScreen = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    MainContent(
        uiState = uiState,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onProfileClick = onProfileClick,
        onRefresh = viewModel::updateData,
        onLoadNextPage = viewModel::loadNextPage,
        onRetry = viewModel::updateData,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    uiState: MainUiState,
    onSearchQueryChange: (String) -> Unit,
    onProfileClick: () -> Unit,
    onRefresh: () -> Unit,
    onLoadNextPage: () -> Unit,
    onRetry: () -> Unit,
) {
    Scaffold(
        topBar = {
            MainTopAppBar(
                searchQuery = uiState.searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                onProfileClick = onProfileClick,
            )
        },
    ) { innerPadding ->
        Surface(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        ) {
            PullToRefreshBox(
                isRefreshing = uiState.isRefreshing,
                onRefresh = onRefresh,
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    if (uiState.launches.isNotEmpty()) {
                        LaunchList(
                            launches = uiState.launches,
                            isNextPageLoading = uiState.isNextPageLoading,
                            isLastPage = uiState.isLastPage,
                            loadNextPage = onLoadNextPage,
                            searchQuery = uiState.searchQuery,
                        )
                    }

                    if (uiState.showLoading) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    if (uiState.showErrorState) {
                        ErrorState(
                            message = uiState.error!!,
                            onRetry = onRetry,
                            modifier = Modifier.align(Alignment.Center),
                        )
                    }

                    if (uiState.showEmptyState) {
                        EmptyState(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    }
}

@Composable
fun LaunchList(
    launches: List<Launch>,
    isNextPageLoading: Boolean,
    isLastPage: Boolean,
    loadNextPage: () -> Unit,
    searchQuery: String,
) {
    val listState = rememberLazyListState()
    val dimensions = LocalDimensions.current
    var lastQuery by remember { mutableStateOf("") }

    LaunchedEffect(searchQuery) {
        if (searchQuery != lastQuery && lastQuery.isNotEmpty()) {
            listState.animateScrollToItem(0)
        }
        lastQuery = searchQuery
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(dimensions.paddingSmall),
    ) {
        itemsIndexed(launches, key = { _, item -> item.id }) { index, launch ->
            LaunchItem(launch)
            if (index >= launches.size - 2 && !isLastPage && !isNextPageLoading) {
                LaunchedEffect(launches.size) {
                    loadNextPage()
                }
            }
        }

        if (isNextPageLoading) {
            item {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(dimensions.paddingMedium),
                    contentAlignment = Alignment.Center,
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
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline,
                )
            }
        }
    }
}

@Composable
fun ErrorState(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(Res.string.main_screen_error_text, message),
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center,
        )
        Button(onClick = onRetry, modifier = Modifier.padding(top = 8.dp)) {
            Text(stringResource(Res.string.main_screen_button_retry_text))
        }
    }
}

@Composable
fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            imageResource(Res.drawable.nothingFound),
            contentDescription = stringResource(Res.string.nothing_found_content_description),
            modifier = Modifier.size(150.dp),
        )
        Text(
            text = stringResource(Res.string.nothing_found_text),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainContent(
        uiState = MainUiState(launches = emptyList(), isLoading = false),
        onSearchQueryChange = {},
        onProfileClick = {},
        onRefresh = {},
        onLoadNextPage = {},
        onRetry = {},
    )
}
