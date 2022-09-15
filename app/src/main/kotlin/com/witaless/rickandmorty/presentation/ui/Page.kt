package com.witaless.rickandmorty.presentation.ui

object Page {

    object Characters {
        const val route = "characters"
    }

    object CharacterDetails {
        const val idNavArgument = "id"

        fun route(id: Int? = null) = "character_details/" + (id ?: "{$idNavArgument}")
    }
}
