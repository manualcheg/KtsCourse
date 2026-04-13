package com.manualcheg.ktscourse.screenSettings.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ktscourse.composeapp.generated.resources.Res
import ktscourse.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onProfileClick: () -> Unit,
    onCompanyHistoryClick: () -> Unit,
    onStatisticsClick: () -> Unit,
    onAboutSpaceXClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.settings_screen_title)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.details_screen_back_button_content_description),
                        )
                    }
                },
            )
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {

            item { SettingSectionTitle(stringResource(Res.string.settings_section_about_spacex)) }
            item {
                SettingClickableItem(
                    title = stringResource(Res.string.settings_item_about_company),
                    icon = Icons.Default.Person,
                    onClick = onAboutSpaceXClick,
                )
            }
            item {
                SettingClickableItem(
                    title = stringResource(Res.string.settings_item_company_history),
                    icon = Icons.Default.History,
                    onClick = onCompanyHistoryClick,
                )
            }
            item {
                SettingClickableItem(
                    title = stringResource(Res.string.settings_item_statistics),
                    icon = Icons.Default.BarChart,
                    onClick = onStatisticsClick,
                )
            }

            item {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                )
            }

            item { SettingSectionTitle(stringResource(Res.string.settings_section_account)) }
            item {
                SettingClickableItem(
                    title = stringResource(Res.string.settings_item_profile),
                    icon = Icons.Default.Person,
                    onClick = onProfileClick,
                )
            }
            item {
                SettingSwitchItem(
                    title = stringResource(Res.string.settings_item_notifications),
                    icon = Icons.Default.Notifications,
                    checked = uiState.isNotificationEnabled,
                    onCheckedChange = { viewModel.toggleNotification(it) },
                )
            }
            item {
                SettingSwitchItem(
                    title = stringResource(Res.string.settings_item_dark_theme),
                    icon = Icons.Default.DarkMode,
                    checked = uiState.isDarkThemeEnabled,
                    onCheckedChange = { viewModel.toggleDarkTheme(it) },
                )
            }
        }
    }
}

@Composable
fun SettingSectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(vertical = 8.dp),
    )
}

@Composable
fun SettingClickableItem(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
fun SettingSwitchItem(
    title: String,
    icon: ImageVector,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(end = 16.dp),
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f),
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
        )
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(
        onBackClick = {},
        onProfileClick = {},
        onCompanyHistoryClick = {},
        onStatisticsClick = {},
        onAboutSpaceXClick = {},
    )
}
