package com.witaless.rickandmorty.presentation.model

data class CharacterDetails(
    val id: Int,
    val name: String,
    val description: String,
    val originLocation: String,
    val lastKnownLocation: String,
    val imageUrl: String,
    val status: CharacterStatus,
    val gender: CharacterGender
)
