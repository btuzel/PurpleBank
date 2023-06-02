package com.example.purplebank.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun LoadingState() {
    LocalFocusManager.current.clearFocus()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 120.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            strokeWidth = 16.dp,
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .size(168.dp)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .paddingFromBaseline(top = 40.dp),
            text = "Loading",
            textAlign = TextAlign.Center,
        )
    }
}