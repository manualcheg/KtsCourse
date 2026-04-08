package com.manualcheg.ktscourse.screenMain.presentation.components

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.manualcheg.ktscourse.common.LocalDimensions
import com.manualcheg.ktscourse.screenMain.domain.model.Launch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import ktscourse.composeapp.generated.resources.Res
import ktscourse.composeapp.generated.resources.launches_list_all_loaded
import org.jetbrains.compose.resources.stringResource

@Composable
fun LaunchList(
    launches: List<Launch>,
    isNextPageLoading: Boolean,
    isLastPage: Boolean,
    loadNextPage: () -> Unit,
    searchQuery: String,
    openLaunchDetails: (String) -> Unit,
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

    LaunchedEffect(listState, isLastPage, isNextPageLoading) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .filter { visibleItems ->
                val lastVisibleItemIndex = visibleItems.lastOrNull()?.index ?: 0
                val totalItemsCount = listState.layoutInfo.totalItemsCount
                lastVisibleItemIndex >= totalItemsCount - 2
            }
            .distinctUntilChanged()
            .collect {
                if (!isLastPage && !isNextPageLoading && launches.isNotEmpty()) {
                    loadNextPage()
                }
            }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(dimensions.paddingSmall),
    ) {
        itemsIndexed(launches, key = { _, item -> item.id }) { _, launch ->
            LaunchItem(launch, { openLaunchDetails(launch.id) })
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
                    text = stringResource(Res.string.launches_list_all_loaded),
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline,
                )
            }
        }
    }
}
