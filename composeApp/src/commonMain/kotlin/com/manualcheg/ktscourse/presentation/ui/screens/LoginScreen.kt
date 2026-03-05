package com.manualcheg.ktscourse.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode.Companion.RevealLastTyped
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecureTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.manualcheg.ktscourse.navigation.Screen
import com.manualcheg.ktscourse.presentation.LocalDimensions
import com.manualcheg.ktscourse.presentation.ViewModelLoginUiScreen
import com.manualcheg.ktscourse.presentation.ui.LoginUiEvent
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import ktscourse.composeapp.generated.resources.Res.*
import ktscourse.composeapp.generated.resources.Res
import ktscourse.composeapp.generated.resources.login_screen_button_login_text
import ktscourse.composeapp.generated.resources.login_screen_textfield_password_label
import ktscourse.composeapp.generated.resources.login_screen_textfield_password_placeholder
import ktscourse.composeapp.generated.resources.login_screen_textfield_username_label
import ktscourse.composeapp.generated.resources.login_screen_textfield_username_placeholder
import org.jetbrains.compose.resources.stringResource

@OptIn(FlowPreview::class)
@Composable
fun LoginScreen(
    viewModel: ViewModelLoginUiScreen,
    navController: NavController
) {
    val dimensions = LocalDimensions.current
    val usernameState = rememberTextFieldState(remember { viewModel.uiState.value.username })
    val passwordState = rememberTextFieldState(remember { viewModel.uiState.value.password })

    val isLoginButtonActive by remember(viewModel) {
        viewModel.uiState
            .map { it.isLoginButtonActive }
            .distinctUntilChanged()
    }.collectAsStateWithLifecycle(viewModel.uiState.value.isLoginButtonActive)

    LaunchedEffect(usernameState) {
        snapshotFlow { usernameState.text.toString() }
            .debounce(500L)
            .collectLatest {
                viewModel.onUsernameChanged(it)
            }
    }

    LaunchedEffect(passwordState) {
        snapshotFlow { passwordState.text.toString() }
            .debounce(500L)
            .collectLatest {
                viewModel.onPasswordChanged(it)
            }
    }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is LoginUiEvent.LoginSuccessEvent -> {
                    navController.navigate(Screen.Main) {
                        popUpTo(Screen.Onboard) { inclusive = true }
                    }
                }
            }
        }
    }

    InputFields(usernameState, passwordState, viewModel, isLoginButtonActive)
}

@Composable
fun InputFields(
    usernameState: TextFieldState,
    passwordState: TextFieldState,
    viewModel: ViewModelLoginUiScreen,
    isLoginButtonActive: Boolean
) {
    val dimensions = LocalDimensions.current
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
            UsernameTextField(usernameState)
            PasswordSecureTextField(passwordState)
            Button(
                onClick = {
                    viewModel.checkCredentials()
                },
                modifier = Modifier.padding(dimensions.paddingStandard),
                enabled = isLoginButtonActive
            ) {
//                Text(stringResource(Res.string.))
                Text(stringResource(Res.string.login_screen_button_login_text))
            }
        }
    }
}

@Composable
fun UsernameTextField(usernameState: TextFieldState) {
    val dimensions = LocalDimensions.current
    TextField(
        state = usernameState,
        modifier = Modifier
            .padding(horizontal = dimensions.paddingStandard)
            .wrapContentSize(),
        label = { Res.string.login_screen_textfield_username_label },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        placeholder = { Res.string.login_screen_textfield_username_placeholder }
    )
}

@Composable
fun PasswordSecureTextField(passwordState: TextFieldState) {
    val dimensions = LocalDimensions.current
    SecureTextField(
        state = passwordState,
        modifier = Modifier
            .wrapContentSize()
            .padding(horizontal = dimensions.paddingStandard),
        label = { Res.string.login_screen_textfield_password_label },
        textObfuscationMode = RevealLastTyped,
        textObfuscationCharacter = Char(42),
        placeholder = { Res.string.login_screen_textfield_password_placeholder },
    )
}
