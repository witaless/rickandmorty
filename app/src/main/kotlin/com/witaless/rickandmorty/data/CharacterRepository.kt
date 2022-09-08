package com.witaless.rickandmorty.data

import com.witaless.rickandmorty.data.model.Character
import com.witaless.rickandmorty.data.model.ListPage
import javax.inject.Inject

class CharacterRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getCharacters(): Result<ListPage<Character>> = runCatching {
        apiService.getCharacters()
    }

    suspend fun getCharacter(id: Int): Result<Character> = runCatching {
        apiService.getCharacter(id)
    }
}
