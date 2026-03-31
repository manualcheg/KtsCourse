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
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchDetailsScreen(
    launchId: String,
    viewModel: LaunchDetailsScreenViewModel = koinInject(),
    onBackClick: () -> Unit,
    onRocketClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchDetailsContent(
        launchId = launchId,
        onBackClick = onBackClick,
        viewModel = viewModel,
        uiState = uiState,
        onRocketClick = onRocketClick,
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
    uiState: DetailsUiState,
    onRocketClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Details", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (!uiState.isLoading && uiState.error == null) {
                        IconButton(onClick = { viewModel.toggleFavorite() }) {
                            Icon(
                                if (uiState.launch.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = if (uiState.launch.isFavorite) Color.Red else Color.Gray,
                            )
                        }

                        IconButton(onClick = { /* TODO share */ }) {
                            Icon(Icons.Default.Share, contentDescription = "Share")
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                item {
                    // Mission Patch
                    AsyncImage(
                        model = launch.patchUrl,
                        contentDescription = "Mission Patch",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Fit,
                    )
                }

                item {
                    // Title and Status
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
                            text = "Flight #${launch.flightNumber}",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Gray,
                        )
                    }
                }

                item {
                    // Dates
                    InfoCard(title = "Launch Date") {
                        Column {
                            Text(
                                "UTC: ${launch.dateUtc}",
                                style = MaterialTheme.typography.bodyLarge,
                            )
                            Text(
                                "Local: ${launch.dateLocal}",
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    }
                }

                if (!launch.details.isNullOrBlank()) {
                    item {
                        InfoCard(title = "Description") {
                            Text(launch.details, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }

                item {
                    // Rocket & Launchpad
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        InfoCard(
                            title = "Rocket",
                            modifier = Modifier.weight(1f)
                                .clickable { onRocketClick(launch.rocketId) },
                        ) {
                            Text(
                                launch.rocketName,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }
                        InfoCard(title = "Launchpad", modifier = Modifier.weight(1f)) {
                            Text(launch.launchpadName)
                        }
                    }
                }

                launch.payloads.let {
                    if (it.isNotEmpty()) {
                        item {
                            InfoCard(title = "Payloads") {
                                Text(launch.payloads.joinToString(", "))
                            }
                        }
                    }
                }

                item {
                    Text(
                        "Links",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        LinkButton("Wiki", launch.wikipediaUrl, Modifier.weight(1f))
                        LinkButton("Reddit", launch.redditUrl, Modifier.weight(1f))
                        LinkButton("Article", launch.articleUrl, Modifier.weight(1f))
                    }
                }

                item {
                    Button(
                        onClick = { /* TODO  Open YouTube */ },
                        enabled = launch.youtubeUrl != null,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0000)),
                    ) {
                        Text("Watch on YouTube", color = Color.White)
                    }
                    Spacer(Modifier.height(24.dp))
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
        LaunchStatus.SUCCESS -> "Success" to Color(0xFF4CAF50)
        LaunchStatus.FAILURE -> "Failed" to Color(0xFFF44336)
        LaunchStatus.UPCOMING -> "Upcoming" to Color(0xFF2196F3)
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
fun LinkButton(label: String, url: String?, modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = { /* TODO Open URL */ },
        enabled = url != null,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(label, fontSize = 12.sp)
    }
}
