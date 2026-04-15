package com.manualcheg.ktscourse.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ktscourse.composeapp.generated.resources.Res
import ktscourse.composeapp.generated.resources.main_screen_button_retry_text
import ktscourse.composeapp.generated.resources.network_error
import org.jetbrains.compose.resources.stringResource

import org.jetbrains.compose.resources.StringResource

@Composable
fun ErrorState(
    message: StringResource?,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        message?.let {
            Text(
                text = stringResource(it),
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
            )
        }
        Button(onClick = onRetry, modifier = Modifier.padding(top = 8.dp)) {
            Text(stringResource(Res.string.main_screen_button_retry_text))
        }
    }
}

@Preview
@Composable
fun PreviewErrorState() {
    ErrorState(
        message = Res.string.network_error,
        onRetry = {},
    )
}
