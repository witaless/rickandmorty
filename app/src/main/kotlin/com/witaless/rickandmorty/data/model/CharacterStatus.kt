package com.witaless.rickandmorty.data.model

import kotlinx.serialization.Serializable

@Serializable
enum class CharacterStatus {
    ALIVE, DEAD, UNKNOWN
}
