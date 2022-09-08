package com.witaless.rickandmorty.data.model

import kotlinx.serialization.Serializable

@Serializable
class ListPage<T>(
    val info: PageInfo,
    val results: List<T>
)
