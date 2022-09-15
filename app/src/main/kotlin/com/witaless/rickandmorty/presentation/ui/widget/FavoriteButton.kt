package com.witaless.rickandmorty.presentation.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.witaless.rickandmorty.R

@Composable
fun FavoriteButton(onClick: () -> Unit, favorite: Boolean) {
    IconButton(onClick = onClick) {
        Image(
            painter = painterResource(
                id = if (favorite) {
                    R.drawable.ic_star_filled
                } else {
                    R.drawable.ic_star_outline
                }
            ),
            contentDescription = stringResource(id = R.string.favorite_button_description)
        )
    }
}
