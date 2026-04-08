package com.manualcheg.ktscourse.screenLogin.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.manualcheg.ktscourse.common.LocalDimensions
import ktscourse.composeapp.generated.resources.Res
import ktscourse.composeapp.generated.resources.login_screen_button_login_text
import ktscourse.composeapp.generated.resources.login_screen_textfield_password_label
import ktscourse.composeapp.generated.resources.login_screen_textfield_password_placeholder
import ktscourse.composeapp.generated.resources.login_screen_textfield_username_label
import ktscourse.composeapp.generated.resources.login_screen_textfield_username_placeholder
import ktscourse.composeapp.generated.resources.login_screen_wrong_password_text
import ktscourse.composeapp.generated.resources.login_screen_wrong_username_text
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    moveToMainScreen: () -> Unit,
    viewModel: ViewModelLoginUiScreen = koinViewModel()
) {
    val dimensions = LocalDimensions.current
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is LoginUiEvent.LoginSuccessEvent -> moveToMainScreen()
                is LoginUiEvent.LoginErrorEvent -> viewModel.makeTextInputInErrorState()
            }
        }
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .safeContentPadding()
                .imePadding()
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            TextField(
                value = uiState.username,
                onValueChange = { viewModel.onUsernameChanged(it) },
                modifier = Modifier
                    .padding(horizontal = dimensions.paddingStandard)
                    .fillMaxWidth(),
                label = { Text(stringResource(Res.string.login_screen_textfield_username_label)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                placeholder = { Text(stringResource(Res.string.login_screen_textfield_username_placeholder)) },
                singleLine = true,
                isError = uiState.error,
                supportingText = {
                    if (uiState.error) {
                        Text(
                            text = stringResource(Res.string.login_screen_wrong_username_text),
                            color = MaterialTheme.colorScheme.error,
                        )
                    }
                },
            )

            Spacer(modifier = Modifier.height(dimensions.paddingMedium))

            TextField(
                value = uiState.password,
                onValueChange = { viewModel.onPasswordChanged(it) },
                modifier = Modifier
                    .padding(horizontal = dimensions.paddingStandard)
                    .fillMaxWidth(),
                label = { Text(stringResource(Res.string.login_screen_textfield_password_label)) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                placeholder = { Text(stringResource(Res.string.login_screen_textfield_password_placeholder)) },
                singleLine = true,
                isError = uiState.error,
                supportingText = {
                    if (uiState.error) {
                        Text(
                            text = stringResource(Res.string.login_screen_wrong_password_text),
                            color = MaterialTheme.colorScheme.error,
                        )
                    }
                },
            )

            Spacer(modifier = Modifier.height(dimensions.paddingStandard))

            Button(
                onClick = { viewModel.checkCredentials() },
                modifier = Modifier.padding(horizontal = dimensions.paddingStandard),
                enabled = uiState.isLoginButtonActive,
            ) {
                Text(stringResource(Res.string.login_screen_button_login_text))
            }
        }
    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
    LoginScreen(moveToMainScreen = {})
}
