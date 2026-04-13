package com.manualcheg.ktscourse.screenRocketDetails.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.manualcheg.ktscourse.screenRocketDetails.domain.RocketDetails
import ktscourse.composeapp.generated.resources.Res
import ktscourse.composeapp.generated.resources.currency_usd
import ktscourse.composeapp.generated.resources.details_screen_active_badge
import ktscourse.composeapp.generated.resources.details_screen_back_arrow_text
import ktscourse.composeapp.generated.resources.details_screen_back_button_content_description
import ktscourse.composeapp.generated.resources.details_screen_boosters_label
import ktscourse.composeapp.generated.resources.details_screen_cost_per_launch_label
import ktscourse.composeapp.generated.resources.details_screen_country_label
import ktscourse.composeapp.generated.resources.details_screen_description_text
import ktscourse.composeapp.generated.resources.details_screen_diameter_label
import ktscourse.composeapp.generated.resources.details_screen_favorite_button_content_description
import ktscourse.composeapp.generated.resources.details_screen_first_flight_label
import ktscourse.composeapp.generated.resources.details_screen_general_info_title
import ktscourse.composeapp.generated.resources.details_screen_height_label
import ktscourse.composeapp.generated.resources.details_screen_inactive_badge
import ktscourse.composeapp.generated.resources.details_screen_mass_label
import ktscourse.composeapp.generated.resources.details_screen_no_description
import ktscourse.composeapp.generated.resources.details_screen_payload_capacity_title
import ktscourse.composeapp.generated.resources.details_screen_rocket_image_content_description
import ktscourse.composeapp.generated.resources.details_screen_share_button_content_description
import ktscourse.composeapp.generated.resources.details_screen_specifications_title
import ktscourse.composeapp.generated.resources.details_screen_stages_label
import ktscourse.composeapp.generated.resources.details_screen_success_rate_label
import ktscourse.composeapp.generated.resources.details_screen_unknown_company
import ktscourse.composeapp.generated.resources.details_screen_view_launches_button
import ktscourse.composeapp.generated.resources.details_screen_wiki_text
import ktscourse.composeapp.generated.resources.n_a
import ktscourse.composeapp.generated.resources.unit_kilograms
import ktscourse.composeapp.generated.resources.unit_meters
import ktscourse.composeapp.generated.resources.unit_percent
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RocketDetailsScreen(
    rocketId: String,
    onBackClick: () -> Unit,
    onLinkClick: (String) -> Unit,
    onViewLaunchesClick: (rocketId: String, rocketName: String) -> Unit,
) {
    val viewModel: RocketDetailsViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(rocketId) {
        viewModel.loadRocketDetails(rocketId)
    }

    RocketDetailsContent(
        viewModel = viewModel,
        onBackClick = onBackClick,
        uiState = uiState,
        onLinkClick = onLinkClick,
        onViewLaunchesClick = onViewLaunchesClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RocketDetailsContent(
    viewModel: RocketDetailsViewModel,
    onBackClick: () -> Unit,
    uiState: RocketDetailsUiState,
    onLinkClick: (String) -> Unit,
    onViewLaunchesClick: (rocketId: String, rocketName: String) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        uiState.rocketDetails?.name
                            ?: stringResource(Res.string.details_screen_back_arrow_text),
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
                    if (!uiState.isLoading && uiState.error == null) {
                        IconButton(onClick = { viewModel.toggleFavorite() }) {
                            Icon(
                                if (uiState.rocketDetails?.isFavorite == true)
                                    Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = stringResource(Res.string.details_screen_favorite_button_content_description),
                                tint = if (uiState.rocketDetails?.isFavorite == true)
                                    Color.Red else Color.Gray,
                            )
                        }

                        IconButton(
                            onClick = { viewModel.shareRocket() },
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
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (uiState.error != null) {
                Text(
                    text = uiState.error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center).padding(16.dp),
                )
            } else {
                uiState.rocketDetails?.let { rocket ->
                    RocketDetailsScrollableContent(
                        rocket,
                        onLinkClick = onLinkClick,
                        onViewLaunchesClick = onViewLaunchesClick,
                    )
                }
            }
        }
    }
}

@Composable
fun RocketDetailsScrollableContent(
    rocket: RocketDetails,
    onLinkClick: (String) -> Unit,
    onViewLaunchesClick: (rocketId: String, rocketName: String) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        if (rocket.flickrImages.isNotEmpty()) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(rocket.flickrImages) { imageUrl ->
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = stringResource(Res.string.details_screen_rocket_image_content_description),
                        modifier = Modifier
                            .width(350.dp)
                            .fillMaxHeight(),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        } else {
            AsyncImage(
                model = rocket.imageUrl,
                contentDescription = rocket.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop,
            )
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = rocket.name,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = rocket.company
                            ?: stringResource(Res.string.details_screen_unknown_company),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
                IsActiveBadge(isActive = rocket.active)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onViewLaunchesClick(rocket.id, rocket.name) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(text = stringResource(Res.string.details_screen_view_launches_button))
            }

            Spacer(modifier = Modifier.height(16.dp))

            InfoSection(title = stringResource(Res.string.details_screen_general_info_title)) {
                InfoRow(
                    stringResource(Res.string.details_screen_country_label),
                    rocket.country ?: stringResource(Res.string.n_a),
                )
                InfoRow(
                    stringResource(Res.string.details_screen_first_flight_label),
                    rocket.firstFlight ?: stringResource(Res.string.n_a),
                )
                InfoRow(
                    stringResource(Res.string.details_screen_cost_per_launch_label),
                    rocket.costPerLaunch?.let { stringResource(Res.string.currency_usd, it) } ?: stringResource(Res.string.n_a),
                )
                rocket.successRatePct?.let {
                    InfoRow(
                        stringResource(Res.string.details_screen_success_rate_label),
                        stringResource(Res.string.unit_percent, it),
                    )
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            Text(
                text = stringResource(Res.string.details_screen_description_text),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = rocket.description
                    ?: stringResource(Res.string.details_screen_no_description),
                style = MaterialTheme.typography.bodyLarge,
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            InfoSection(title = stringResource(Res.string.details_screen_specifications_title)) {
                InfoRow(
                    stringResource(Res.string.details_screen_height_label),
                    stringResource(Res.string.unit_meters, rocket.height ?: 0.0),
                )
                InfoRow(
                    stringResource(Res.string.details_screen_diameter_label),
                    stringResource(Res.string.unit_meters, rocket.diameter ?: 0.0),
                )
                InfoRow(
                    stringResource(Res.string.details_screen_mass_label),
                    stringResource(Res.string.unit_kilograms, rocket.mass ?: 0.0),
                )
                InfoRow(
                    stringResource(Res.string.details_screen_stages_label),
                    "${rocket.stages ?: 0}",
                )
                InfoRow(
                    stringResource(Res.string.details_screen_boosters_label),
                    "${rocket.boosters ?: 0}",
                )
            }

            if (rocket.payloadWeights.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(Res.string.details_screen_payload_capacity_title),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
                rocket.payloadWeights.forEach { (name, kg) ->
                    InfoRow(name, stringResource(Res.string.unit_kilograms, kg))
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            rocket.wikipedia?.let { url ->
                Text(
                    text = stringResource(Res.string.details_screen_wiki_text),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = url,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline,
                    ),
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .clickable { onLinkClick(url) },
                )
            }
        }
    }
}

@Composable
fun InfoSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(8.dp))
        content()
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
fun IsActiveBadge(isActive: Boolean) {
    val color = if (isActive) Color(0xFF4CAF50) else Color(0xFFF44336)
    Surface(
        color = color.copy(alpha = 0.1f),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, color),
    ) {
        Text(
            text = if (isActive) stringResource(Res.string.details_screen_active_badge) else stringResource(
                Res.string.details_screen_inactive_badge,
            ),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            color = color,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
        )
    }
}
