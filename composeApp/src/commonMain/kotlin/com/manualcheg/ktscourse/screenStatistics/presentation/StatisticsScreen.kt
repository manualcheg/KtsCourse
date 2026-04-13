package com.manualcheg.ktscourse.screenStatistics.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manualcheg.ktscourse.screenStatistics.domain.Statistics
import ktscourse.composeapp.generated.resources.Res
import ktscourse.composeapp.generated.resources.details_screen_back_button_content_description
import ktscourse.composeapp.generated.resources.settings_item_statistics
import ktscourse.composeapp.generated.resources.unknown_error
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    onNavigateUp: () -> Unit,
    viewModel: ViewModelStatisticsScreen = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.settings_item_statistics)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.details_screen_back_button_content_description),
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            when {
                uiState.isLoading && uiState.statistics == null -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                uiState.error != null && uiState.statistics == null -> {
                    Text(
                        text = uiState.error ?: stringResource(Res.string.unknown_error),
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.error,
                    )
                }

                uiState.statistics != null -> {
                    StatisticsContent(uiState.statistics!!)
                }
            }
        }
    }
}

@Composable
fun StatisticsContent(stats: Statistics) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            ),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                StatisticsRow("Total Launches", stats.totalLaunches.toString())
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                StatisticsRow("Success Launches", stats.successLaunches.toString())
                StatisticsRow("Failed Launches", stats.failedLaunches.toString())
                StatisticsRow("Upcoming Launches", stats.upcomingLaunches.toString())
            }
        }

        if (stats.launchesByYear.isNotEmpty()) {
            Text(
                text = "Launches by Year",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp),
            )
            LaunchesChart(
                launchesByYear = stats.launchesByYear,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(vertical = 16.dp),
            )
        }
    }
}

@Composable
fun LaunchesChart(
    launchesByYear: Map<Int, Int>,
    modifier: Modifier = Modifier,
) {
    val years = launchesByYear.keys.toList()
    val counts = launchesByYear.values.toList()
    val maxCount = (counts.maxOrNull() ?: 1).toFloat()

    val barColor = MaterialTheme.colorScheme.primary
    val labelColor = MaterialTheme.colorScheme.onSurfaceVariant
    val textMeasurer = rememberTextMeasurer()

    val countStyle = MaterialTheme.typography.labelSmall.copy(
        color = labelColor,
        fontSize = 9.sp,
        fontWeight = FontWeight.Bold,
    )
    val yearStyle = MaterialTheme.typography.labelSmall.copy(
        color = labelColor,
        fontSize = 10.sp,
    )

    Canvas(modifier = modifier) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        // Выделяем место под числа сверху и годы снизу
        val topPadding = 24.dp.toPx()
        val bottomPadding = 24.dp.toPx()
        val chartHeight = canvasHeight - topPadding - bottomPadding

        val barWidth = canvasWidth / (years.size * 1.5f)
        val spacing = (canvasWidth - (barWidth * years.size)) / (years.size + 1)

        years.forEachIndexed { index, year ->
            val count = counts[index]
            val barHeight = (count / maxCount) * chartHeight

            val x = spacing + index * (barWidth + spacing)
            val yBarTop = topPadding + (chartHeight - barHeight)

            // Столбец
            drawRect(
                color = barColor,
                topLeft = Offset(x, yBarTop),
                size = Size(barWidth, barHeight),
            )

            // Число над столбцом
            val countLayout = textMeasurer.measure(count.toString(), countStyle)
            drawText(
                textLayoutResult = countLayout,
                topLeft = Offset(
                    x = x + (barWidth - countLayout.size.width) / 2,
                    y = yBarTop - countLayout.size.height - 4.dp.toPx(),
                ),
            )

            // Год под столбцом (каждый второй, если их много, чтобы не сливались)
            val step = if (years.size > 12) 2 else 1
            if (index % step == 0) {
                val yearLayout = textMeasurer.measure(year.toString(), yearStyle)
                drawText(
                    textLayoutResult = yearLayout,
                    topLeft = Offset(
                        x = x + (barWidth - yearLayout.size.width) / 2,
                        y = topPadding + chartHeight + 4.dp.toPx(),
                    ),
                )
            }
        }
    }
}

@Composable
fun StatisticsRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
    }
}
