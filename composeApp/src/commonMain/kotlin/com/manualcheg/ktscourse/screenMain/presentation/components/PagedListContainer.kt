package com.manualcheg.ktscourse.screenMain.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.manualcheg.ktscourse.common.components.EmptyState
import com.manualcheg.ktscourse.common.components.ErrorState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PagedListContainer(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    showLoading: Boolean,
    showErrorState: Boolean,
    showEmptyState: Boolean,
    error: String?,
    onRetry: () -> Unit,
    content: @Composable () -> Unit
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (!showLoading && !showErrorState && !showEmptyState) {
                content()
            }

            if (showLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            if (showErrorState) {
                ErrorState(
                    message = error ?: "Unknown error",
                    onRetry = onRetry,
                    modifier = Modifier.align(Alignment.Center),
                )
            }

            if (showEmptyState) {
                EmptyState(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
