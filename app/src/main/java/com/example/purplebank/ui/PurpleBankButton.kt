package com.example.purplebank.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
@NonRestartableComposable
fun PurpleBankButton(
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    PurpleBankButton(text = stringResource(text), modifier, enabled, onClick)
}

@Composable
fun PurpleBankButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.heightIn(min = 56.dp),
        enabled = enabled,
        onClick = onClick,
    ) {
        Text(text = text)
    }
}