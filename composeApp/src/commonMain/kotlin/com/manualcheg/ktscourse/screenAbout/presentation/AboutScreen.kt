package com.manualcheg.ktscourse.screenAbout.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.manualcheg.ktscourse.common.components.ErrorState
import com.manualcheg.ktscourse.data.models.CompanyDto
import ktscourse.composeapp.generated.resources.Res
import ktscourse.composeapp.generated.resources.about_screen_address
import ktscourse.composeapp.generated.resources.about_screen_ceo
import ktscourse.composeapp.generated.resources.about_screen_city
import ktscourse.composeapp.generated.resources.about_screen_coo
import ktscourse.composeapp.generated.resources.about_screen_cto
import ktscourse.composeapp.generated.resources.about_screen_employees
import ktscourse.composeapp.generated.resources.about_screen_founded
import ktscourse.composeapp.generated.resources.about_screen_founder
import ktscourse.composeapp.generated.resources.about_screen_headquarters
import ktscourse.composeapp.generated.resources.about_screen_launch_sites
import ktscourse.composeapp.generated.resources.about_screen_leadership
import ktscourse.composeapp.generated.resources.about_screen_state
import ktscourse.composeapp.generated.resources.about_screen_test_sites
import ktscourse.composeapp.generated.resources.about_screen_title
import ktscourse.composeapp.generated.resources.about_screen_valuation
import ktscourse.composeapp.generated.resources.about_screen_vehicles
import ktscourse.composeapp.generated.resources.details_screen_back_button_content_description
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    onNavigateUp: () -> Unit,
    viewModel: ViewModelAboutScreen = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.about_screen_title)) },
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
                uiState.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                uiState.error != null -> {
                    ErrorState(
                        message = uiState.error,
                        onRetry = { viewModel.loadCompanyInfo() },
                        modifier = Modifier.align(Alignment.Center),
                    )
                }

                uiState.companyInfo != null -> {
                    AboutContent(uiState.companyInfo!!)
                }
            }
        }
    }
}

@Composable
fun AboutContent(info: CompanyDto) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            Text(
                text = info.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )
        }
        item {
            Text(
                text = info.summary,
                style = MaterialTheme.typography.bodyLarge,
            )
        }

        item { HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp)) }

        item { InfoRow(stringResource(Res.string.about_screen_founder), info.founder) }
        item { InfoRow(stringResource(Res.string.about_screen_founded), info.founded.toString()) }
        item { InfoRow(stringResource(Res.string.about_screen_employees), info.employees.toString()) }
        item { InfoRow(stringResource(Res.string.about_screen_vehicles), info.vehicles.toString()) }
        item { InfoRow(stringResource(Res.string.about_screen_launch_sites), info.launchSites.toString()) }
        item { InfoRow(stringResource(Res.string.about_screen_test_sites), info.testSites.toString()) }
        item {
            val formattedValuation = info.valuation.toString()
                .reversed()
                .chunked(3)
                .joinToString(" ")
                .reversed()
            InfoRow(
                label = stringResource(Res.string.about_screen_valuation),
                value = "$formattedValuation $",
            )
        }

        item { HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp)) }

        item { Text(stringResource(Res.string.about_screen_leadership), style = MaterialTheme.typography.titleLarge) }
        item { InfoRow(stringResource(Res.string.about_screen_ceo), info.ceo) }
        item { InfoRow(stringResource(Res.string.about_screen_coo), info.coo) }
        item { InfoRow(stringResource(Res.string.about_screen_cto), info.cto) }

        item { HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp)) }

        item { Text(stringResource(Res.string.about_screen_headquarters), style = MaterialTheme.typography.titleLarge) }
        item { InfoRow(stringResource(Res.string.about_screen_address), info.headquarters.address) }
        item { InfoRow(stringResource(Res.string.about_screen_city), info.headquarters.city) }
        item { InfoRow(stringResource(Res.string.about_screen_state), info.headquarters.state) }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.secondary,
        )
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview
@Composable
fun AboutScreenPreview() {
    AboutScreen(onNavigateUp = {})
}
