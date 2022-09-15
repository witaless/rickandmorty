package com.witaless.rickandmorty.presentation.ui.widget

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun ColoredBadge(@StringRes stringRes: Int, @ColorRes colorRes: Int) {
    Text(
        modifier = Modifier
            .background(
                color = colorResource(id = colorRes),
                shape = RoundedCornerShape(50.dp)
            )
            .padding(vertical = 8.dp, horizontal = 10.dp),
        text = stringResource(id = stringRes),
        style = MaterialTheme.typography.body2
    )
}
