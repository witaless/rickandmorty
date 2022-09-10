package com.witaless.rickandmorty.data

import com.witaless.rickandmorty.data.model.Character
import com.witaless.rickandmorty.data.model.ListPage
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): ListPage<Character>

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Character

    @GET("character/{ids}")
    suspend fun getCharactersById(@Path("ids") ids: String): List<Character>
}
