package com.witaless.rickandmorty.presentation.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.witaless.rickandmorty.R

@Composable
fun ErrorContent(onTryAgainClick: () -> Unit) {
    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ErrorText()
            TextButton(onClick = onTryAgainClick) {
                Text(text = stringResource(id = R.string.try_again_button))
            }
        }
    }
}

@Composable
fun ErrorText() {
    Text(
        text = stringResource(id = R.string.error_text),
        style = MaterialTheme.typography.caption
    )
}
