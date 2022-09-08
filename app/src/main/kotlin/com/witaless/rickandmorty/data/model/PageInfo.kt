package com.witaless.rickandmorty.data.model

import kotlinx.serialization.Serializable

@Serializable
class PageInfo(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)