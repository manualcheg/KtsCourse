package com.manualcheg.ktscourse.screenRockets.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.manualcheg.ktscourse.common.LocalDimensions
import com.manualcheg.ktscourse.screenMain.presentation.components.RocketItem
import com.manualcheg.ktscourse.screenRockets.presentation.RocketListUiState
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import ktscourse.composeapp.generated.resources.Res
import ktscourse.composeapp.generated.resources.rockets_list_all_loaded
import org.jetbrains.compose.resources.stringResource

@Composable
fun RocketList(
    uiState: RocketListUiState,
    loadNextPage: () -> Unit,
    openRocketDetails: (id: String) -> Unit,
) {
    val listState = rememberLazyListState()
    val dimensions = LocalDimensions.current

    LaunchedEffect(uiState.searchQuery) {
        if (uiState.searchQuery.isNotEmpty()) {
            listState.animateScrollToItem(0)
        }
    }

    LaunchedEffect(listState, uiState.isLastPage, uiState.isNextPageLoading) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .filter { visibleItems ->
                val lastVisibleItemIndex = visibleItems.lastOrNull()?.index ?: 0
                val totalItemsCount = listState.layoutInfo.totalItemsCount
                lastVisibleItemIndex >= totalItemsCount - 2
            }
            .distinctUntilChanged()
            .collect {
                if (!uiState.isLastPage && !uiState.isNextPageLoading && uiState.items.isNotEmpty()) {
                    loadNextPage()
                }
            }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(dimensions.paddingSmall),
    ) {
        itemsIndexed(uiState.items, key = { _, item -> item.id }) { _, rocket ->
            RocketItem(rocket, { openRocketDetails(rocket.id) })
        }

        if (uiState.isNextPageLoading) {
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

        if (uiState.isLastPage && uiState.items.isNotEmpty()) {
            item {
                Text(
                    text = stringResource(Res.string.rockets_list_all_loaded),
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline,
                )
            }
        }
    }
}
