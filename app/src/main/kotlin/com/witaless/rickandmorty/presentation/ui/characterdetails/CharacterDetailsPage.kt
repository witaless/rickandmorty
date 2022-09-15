package com.witaless.rickandmorty.presentation.ui.characterdetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.witaless.rickandmorty.R
import com.witaless.rickandmorty.presentation.model.CharacterDetails
import com.witaless.rickandmorty.presentation.ui.widget.ColoredBadge
import com.witaless.rickandmorty.presentation.ui.widget.ErrorText
import com.witaless.rickandmorty.presentation.ui.widget.FavoriteButton

@Composable
fun CharacterDetailsPage(
    viewModel: CharacterDetailsViewModel,
    navController: NavController
) {
    val characterDetails by viewModel.character.observeAsState()
    val isProgress by viewModel.progress.observeAsState(false)
    val isError by viewModel.showError.observeAsState(false)
    val showContent by viewModel.showContent.observeAsState(false)
    val isFavorite by viewModel.isFavorite.observeAsState(false)

    Scaffold { padding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            AnimatedVisibility(
                enter = fadeIn(),
                exit = fadeOut(),
                visible = showContent
            ) {
                characterDetails?.let {
                    CharacterDetailsContent(it)
                }
            }

            AnimatedVisibility(
                modifier = Modifier.align(Alignment.Center),
                enter = fadeIn(),
                exit = fadeOut(),
                visible = isError
            ) {
                ErrorText()
            }

            AnimatedVisibility(
                modifier = Modifier.align(Alignment.Center),
                enter = fadeIn(),
                exit = fadeOut(),
                visible = isProgress
            ) {
                CircularProgressIndicator()
            }

            TopBar(
                isFavorite = isFavorite,
                onFavoriteClick = { viewModel.toggleFavoriteState() },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

@Composable
private fun CharacterDetailsContent(characterDetails: CharacterDetails) {
    Column {
        CharacterHeader(
            name = characterDetails.name,
            description = characterDetails.description,
            imageUrl = characterDetails.imageUrl
        )
        Spacer(modifier = Modifier.size(16.dp))
        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            ColoredBadge(
                stringRes = characterDetails.status.stringRes,
                colorRes = characterDetails.status.colorRes
            )
            Spacer(modifier = Modifier.size(8.dp))
            ColoredBadge(
                stringRes = characterDetails.gender.stringRes,
                colorRes = characterDetails.gender.colorRes
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(id = R.string.origin_location_title),
            style = MaterialTheme.typography.body1
        )
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = characterDetails.originLocation,
            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(id = R.string.last_known_location_title),
            style = MaterialTheme.typography.body1
        )
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = characterDetails.lastKnownLocation,
            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
private fun CharacterHeader(name: String, description: String, imageUrl: String) {
    Box {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.placeholder)),
            model = imageUrl,
            contentDescription = name
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(Color.Transparent, Color.Black)
                    )
                )
                .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(text = name, style = MaterialTheme.typography.h4, color = Color.White)
            Text(text = description, style = MaterialTheme.typography.caption, color = Color.White)
        }
    }
}

@Composable
private fun TopBar(isFavorite: Boolean, onFavoriteClick: () -> Unit, onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            modifier = Modifier.background(
                Brush.radialGradient(
                    listOf(
                        Color.Black.copy(alpha = 0.1f),
                        Color.Transparent
                    )
                )
            ), onClick = onBackClick
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = stringResource(id = R.string.back_button_description)
            )
        }
        Box(
            modifier = Modifier.background(
                Brush.radialGradient(
                    listOf(
                        Color.Black.copy(alpha = 0.1f),
                        Color.Transparent
                    )
                )
            )
        ) {
            FavoriteButton(onClick = onFavoriteClick, favorite = isFavorite)
        }
    }
}
