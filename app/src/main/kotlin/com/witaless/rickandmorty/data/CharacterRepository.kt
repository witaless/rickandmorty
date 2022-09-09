package com.witaless.rickandmorty.data

import com.witaless.rickandmorty.data.model.Character
import com.witaless.rickandmorty.data.model.toItem
import com.witaless.rickandmorty.presentation.model.CharacterItem
import javax.inject.Inject

class CharacterRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getCharacters(): Result<List<CharacterItem>> = runCatching {
        apiService.getCharacters().results.map { it.toItem() }
    }

    suspend fun getCharacter(id: Int): Result<Character> = runCatching {
        apiService.getCharacter(id)
    }
}
