package com.manualcheg.ktscourse.screenLaunchDetails.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.manualcheg.ktscourse.common.LaunchStatus
import com.manualcheg.ktscourse.common.components.ErrorState
import ktscourse.composeapp.generated.resources.Res
import ktscourse.composeapp.generated.resources.details_screen_article_text
import ktscourse.composeapp.generated.resources.details_screen_back_arrow_text
import ktscourse.composeapp.generated.resources.details_screen_back_button_content_description
import ktscourse.composeapp.generated.resources.details_screen_description_text
import ktscourse.composeapp.generated.resources.details_screen_favorite_button_content_description
import ktscourse.composeapp.generated.resources.details_screen_flight_number_text
import ktscourse.composeapp.generated.resources.details_screen_launch_date_text
import ktscourse.composeapp.generated.resources.details_screen_launchpad_text
import ktscourse.composeapp.generated.resources.details_screen_links_text
import ktscourse.composeapp.generated.resources.details_screen_patch_content_description
import ktscourse.composeapp.generated.resources.details_screen_payloads_text
import ktscourse.composeapp.generated.resources.details_screen_reddit_text
import ktscourse.composeapp.generated.resources.details_screen_rocket_text
import ktscourse.composeapp.generated.resources.details_screen_share_button_content_description
import ktscourse.composeapp.generated.resources.details_screen_time_local_text
import ktscourse.composeapp.generated.resources.details_screen_time_utc_text
import ktscourse.composeapp.generated.resources.details_screen_wiki_text
import ktscourse.composeapp.generated.resources.details_screen_youtube_text
import ktscourse.composeapp.generated.resources.main_screen_launch_status_failed_text
import ktscourse.composeapp.generated.resources.main_screen_launch_status_success_text
import ktscourse.composeapp.generated.resources.main_screen_launch_status_upcoming_text
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchDetailsScreen(
    launchId: String,
    viewModel: LaunchDetailsScreenViewModel = koinInject(),
    onBackClick: () -> Unit,
    onRocketClick: (String) -> Unit,
    openLink: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchDetailsContent(
        launchId = launchId,
        onBackClick = onBackClick,
        viewModel = viewModel,
        uiState = uiState,
        onRocketClick = onRocketClick,
        openLink = openLink,
    )

    LaunchedEffect(launchId) {
        viewModel.loadLaunchDetails(launchId)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchDetailsContent(
    launchId: String,
    onBackClick: () -> Unit,
    viewModel: LaunchDetailsScreenViewModel,
    uiState: LaunchDetailsUiState,
    onRocketClick: (String) -> Unit,
    openLink: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(Res.string.details_screen_back_arrow_text),
                        fontWeight = FontWeight.Bold,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.details_screen_back_button_content_description),
                        )
                    }
                },
                actions = {
                    if (!uiState.isLoading && uiState.error == null && uiState.launch != null) {
                        IconButton(onClick = { viewModel.toggleFavorite() }) {
                            Icon(
                                if (uiState.launch.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = stringResource(Res.string.details_screen_favorite_button_content_description),
                                tint = if (uiState.launch.isFavorite) Color.Red else Color.Gray,
                            )
                        }

                        IconButton(
                            onClick = { viewModel.shareLaunch() },
                            enabled = !uiState.isLoading,
                        ) {
                            Icon(
                                Icons.Default.Share,
                                contentDescription = stringResource(Res.string.details_screen_share_button_content_description),
                            )
                        }
                    }
                },
            )
        },
    ) { padding ->
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.error != null) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                ErrorState(
                    message = uiState.error,
                    onRetry = { viewModel.loadLaunchDetails(launchId) },
                )
            }
        } else {
            val launch = uiState.launch
            if (launch != null) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    item {
                        AsyncImage(
                            model = launch.patchUrl,
                            contentDescription = stringResource(Res.string.details_screen_patch_content_description),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Fit,
                        )
                    }

                    item {
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Text(
                                    text = launch.name,
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.weight(1f),
                                )
                                StatusBadge(launch.status)
                            }
                            Text(
                                text = stringResource(
                                    Res.string.details_screen_flight_number_text,
                                    launch.flightNumber,
                                ),
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.Gray,
                            )
                        }
                    }

                    item {
                        InfoCard(title = stringResource(Res.string.details_screen_launch_date_text)) {
                            Column {
                                Text(
                                    stringResource(
                                        Res.string.details_screen_time_utc_text,
                                        launch.dateUtc,
                                    ),
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                                Text(
                                    stringResource(
                                        Res.string.details_screen_time_local_text,
                                        launch.dateLocal,
                                    ),
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                            }
                        }
                    }

                    if (!launch.details.isNullOrBlank()) {
                        item {
                            InfoCard(title = stringResource(Res.string.details_screen_description_text)) {
                                Text(launch.details, style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }

                    item {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            InfoCard(
                                title = stringResource(Res.string.details_screen_rocket_text),
                                modifier = Modifier.weight(1f)
                                    .clickable { onRocketClick(launch.rocketId) },
                            ) {
                                Text(
                                    launch.rocketName,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                )
                            }
                            InfoCard(
                                title = stringResource(Res.string.details_screen_launchpad_text),
                                modifier = Modifier.weight(1f),
                            ) {
                                Text(launch.launchpadName)
                            }
                        }
                    }

                    launch.payloads.let {
                        if (it.isNotEmpty()) {
                            item {
                                InfoCard(title = stringResource(Res.string.details_screen_payloads_text)) {
                                    Text(launch.payloads.joinToString(", "))
                                }
                            }
                        }
                    }

                    item {
                        Text(
                            stringResource(Res.string.details_screen_links_text),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            LinkButton(
                                stringResource(Res.string.details_screen_wiki_text),
                                launch.wikipediaUrl,
                                Modifier.weight(1f),
                                openLink,
                            )
                            LinkButton(
                                stringResource(Res.string.details_screen_reddit_text),
                                launch.redditUrl,
                                Modifier.weight(1f),
                                openLink,
                            )
                            LinkButton(
                                stringResource(Res.string.details_screen_article_text),
                                launch.articleUrl,
                                Modifier.weight(1f),
                                openLink,
                            )
                        }
                    }

                    item {
                        Button(
                            onClick = { openLink.invoke(uiState.launch.youtubeUrl ?: "") },
                            enabled = launch.youtubeUrl != null,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0000)),
                        ) {
                            Text(
                                stringResource(Res.string.details_screen_youtube_text),
                                color = Color.White,
                            )
                        }
                        Spacer(Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun InfoCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                alpha = 0.5f,
            ),
        ),
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(
                title,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary,
            )
            Spacer(Modifier.height(4.dp))
            content()
        }
    }
}

@Composable
fun StatusBadge(status: LaunchStatus) {
    val (text, color) = when (status) {
        LaunchStatus.SUCCESS -> stringResource(Res.string.main_screen_launch_status_success_text) to Color(
            0xFF4CAF50,
        )

        LaunchStatus.FAILURE -> stringResource(Res.string.main_screen_launch_status_failed_text) to Color(
            0xFFF44336,
        )

        LaunchStatus.UPCOMING -> stringResource(Res.string.main_screen_launch_status_upcoming_text) to Color(
            0xFF2196F3,
        )
    }
    Surface(
        color = color.copy(alpha = 0.1f),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, color),
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            color = color,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun LinkButton(
    label: String,
    url: String?,
    modifier: Modifier = Modifier,
    openLink: (String) -> Unit
) {
    OutlinedButton(
        onClick = { openLink.invoke(url ?: "") },
        enabled = url != null,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(label, fontSize = 12.sp)
    }
}
