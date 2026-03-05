package com.manualcheg.ktscourse.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextObfuscationMode.Companion.RevealLastTyped
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecureTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.manualcheg.ktscourse.ui.LocalDimensions

@Composable
fun LoginScreen() {
    val dimensions = LocalDimensions.current

    Scaffold() { innerPadding ->
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
                state = rememberTextFieldState(""),
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = dimensions.paddingStandard),
                label = { Text("Email") },
                lineLimits = TextFieldLineLimits.SingleLine,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                placeholder = { Text("example@gmail.com") }
            )
            SecureTextField(
                state = rememberTextFieldState(""),
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = dimensions.paddingStandard),
                label = { Text("Password") },
                textObfuscationMode = RevealLastTyped,
                textObfuscationCharacter = Char(42),
                placeholder = { Text("P@ssw0rd") },
            )
            Button(
                onClick = { },
                modifier = Modifier.padding(dimensions.paddingStandard)
            ) {
                Text("Login")
            }
        }
    }
}

@Composable
@Preview
fun previewLogin() {
    LoginScreen()
}
