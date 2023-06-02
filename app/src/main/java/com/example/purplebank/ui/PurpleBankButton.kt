package com.example.purplebank.ui

import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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