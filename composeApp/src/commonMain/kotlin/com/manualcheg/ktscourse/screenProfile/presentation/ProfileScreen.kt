package com.manualcheg.ktscourse.screenProfile.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.manualcheg.ktscourse.common.LocalDimensions
import ktscourse.composeapp.generated.resources.Res
import ktscourse.composeapp.generated.resources.profile_screen_back_arrow_text
import ktscourse.composeapp.generated.resources.profile_screen_button_logout_text
import ktscourse.composeapp.generated.resources.profile_screen_icon_content_descript
import ktscourse.composeapp.generated.resources.profile_screen_profile_text
import ktscourse.composeapp.generated.resources.profile_screen_username_text
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ProfileScreen(
    moveToLoginScreen: () -> Unit,
    onNavigateUp: () -> Unit,
    viewModel: ViewModelProfileScreen = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val dimensions = LocalDimensions.current

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                ProfileUiEvent.Logout -> moveToLoginScreen()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.profile_screen_back_arrow_text)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.profile_screen_back_arrow_text),
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = stringResource(Res.string.profile_screen_icon_content_descript),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(dimensions.profileScreenIconSize),
            )
            Spacer(modifier = Modifier.height(dimensions.paddingLarge))
            Text(
                stringResource(Res.string.profile_screen_profile_text),
                modifier = Modifier.padding(bottom = dimensions.paddingMedium),
                style = MaterialTheme.typography.headlineMedium,
            )
            Text(stringResource(Res.string.profile_screen_username_text, uiState.username))
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { viewModel.logout() },
                modifier = Modifier.fillMaxWidth().padding(horizontal = dimensions.paddingMedium),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                    ),
            ) {
                Text(stringResource(Res.string.profile_screen_button_logout_text))
            }
        }
    }
}

@Composable
@Preview
fun PreviewProfileScreen() {
    ProfileScreen(
        moveToLoginScreen = {},
        onNavigateUp = {},
    )
}
