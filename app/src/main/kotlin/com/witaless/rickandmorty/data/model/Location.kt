package com.witaless.rickandmorty.data.model

import kotlinx.serialization.Serializable

@Serializable
class Location(
    val name: String,
    val url: String
)