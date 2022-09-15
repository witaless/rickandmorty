package com.witaless.rickandmorty.presentation.ui.characters

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.witaless.rickandmorty.R
import com.witaless.rickandmorty.presentation.model.CharacterItem
import com.witaless.rickandmorty.presentation.ui.Page
import com.witaless.rickandmorty.presentation.ui.characters.widget.CharacterCard
import com.witaless.rickandmorty.presentation.ui.widget.ErrorContent
import com.witaless.rickandmorty.presentation.ui.widget.FavoriteButton


@Composable
fun CharactersPage(
    viewModel: CharactersViewModel,
    navController: NavController
) {
    val characters by viewModel.characters.observeAsState(emptyList())
    val isFavoriteView by viewModel.isFavoriteViewEnabled.observeAsState(false)
    val isError by viewModel.showError.observeAsState(false)
    val isProgress by viewModel.progress.observeAsState(false)

    SideEffect {
        viewModel.updateFavoritesStatusIfNotLoading()
    }

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.h6,
                    color = Color.White
                )
            },
            actions = {
                FavoriteButton(
                    onClick = { viewModel.showFavoritesClicked() },
                    favorite = isFavoriteView
                )
            }
        )
    }) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Crossfade(
                targetState = isError
            ) { isError ->
                if (isError) {
                    ErrorContent(onTryAgainClick = { viewModel.reload() })
                } else {
                    CharactersList(
                        characters = characters,
                        onItemClick = { id: Int ->
                            navController.navigate(Page.CharacterDetails.route(id))
                        },
                        onToggleFavoriteClick = { id: Int ->
                            viewModel.toggleItemFavoriteState(id)
                        },
                        onLoadMore = { viewModel.loadMore() }
                    )
                }
            }

            AnimatedVisibility(
                modifier = Modifier.align(Alignment.Center),
                enter = fadeIn(),
                exit = fadeOut(),
                visible = isProgress
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CharactersList(
    characters: List<CharacterItem>,
    onItemClick: (Int) -> Unit,
    onToggleFavoriteClick: (Int) -> Unit,
    onLoadMore: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(vertical = 4.dp)
    ) {
        itemsIndexed(
            items = characters,
            key = { _, item -> item.id }
        ) { index, item ->
            CharacterCard(
                modifier = Modifier.animateItemPlacement(),
                character = item,
                onItemClick = onItemClick,
                onToggleFavoriteClick = onToggleFavoriteClick
            )
            SideEffect {
                if (index == characters.lastIndex) {
                    onLoadMore()
                }
            }
        }
    }
}
