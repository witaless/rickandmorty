package com.witaless.rickandmorty.data

import com.witaless.rickandmorty.data.model.Character
import com.witaless.rickandmorty.data.model.ListPage
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("character")
    suspend fun getCharacters(): ListPage<Character>

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Character
}
