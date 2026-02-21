package com.manualcheg.ktscourse.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextObfuscationMode.Companion.RevealLastTyped
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecureTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun LoginScreen() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            TextField(
                state = rememberTextFieldState(""),
                modifier = Modifier.padding(start = 40.dp, end = 40.dp),
                label = { Text("Email") },
                lineLimits = TextFieldLineLimits.SingleLine,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            SecureTextField(
                state = rememberTextFieldState(""),
                modifier = Modifier.padding(start = 40.dp, end = 40.dp),
                label = { Text("Password") },
                textObfuscationMode = RevealLastTyped,
                textObfuscationCharacter = Char(42)
            )
            Button(
                onClick = { },
                modifier = Modifier.padding(40.dp)
            ) {
                Text("Login")
            }
        }
    }
}