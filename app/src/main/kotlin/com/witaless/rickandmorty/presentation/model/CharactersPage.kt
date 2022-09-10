package com.witaless.rickandmorty.presentation.model

data class CharactersPage(
    val items: List<CharacterItem>,
    val isLastPage: Boolean
)