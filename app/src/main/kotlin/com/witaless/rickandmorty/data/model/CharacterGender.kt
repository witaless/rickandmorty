package com.witaless.rickandmorty.data.model

import kotlinx.serialization.Serializable

@Serializable
enum class CharacterGender {
    FEMALE, MALE, GENDERLESS, UNKNOWN
}
