package com.witaless.rickandmorty.presentation.model

data class CharacterItem(
    val id: Int,
    val name: String,
    val species: String,
    val imageUrl: String,
    val status: CharacterStatus,
    val gender: CharacterGender
)
