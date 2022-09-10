package com.witaless.rickandmorty.data

import com.witaless.rickandmorty.data.model.Character
import com.witaless.rickandmorty.data.model.toItem
import com.witaless.rickandmorty.presentation.model.CharacterItem
import com.witaless.rickandmorty.presentation.model.CharactersPage
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val apiService: ApiService,
    private val favoriteCharactersIdStorage: FavoriteCharactersIdStorage
) {

    suspend fun getCharacters(page: Int = 0): Result<CharactersPage> = runCatching {
        val listPage = apiService.getCharacters(page)
        CharactersPage(
            items = listPage.results.map { it.toItem() },
            isLastPage = listPage.info.next == null
        )
    }

    suspend fun getFavoriteCharacters(): Result<List<CharacterItem>> = runCatching {
        val ids = favoriteCharactersIdStorage.getFavoriteIds().first()
        if (ids.isEmpty()) {
            return@runCatching emptyList()
        }
        apiService.getCharactersById(ids.joinToString(separator = ",", postfix = ",")).map {
            it.toItem(isFavorite = true)
        }
    }

    suspend fun getFavoriteCharacterIds(): Result<List<Int>> = runCatching {
        favoriteCharactersIdStorage.getFavoriteIds().first()
    }

    suspend fun getCharacter(id: Int): Result<Character> = runCatching {
        apiService.getCharacter(id)
    }

    suspend fun toggleItemFavoriteState(id: Int) = runCatching {
        val ids = favoriteCharactersIdStorage.getFavoriteIds().first()
        if (ids.contains(id)) {
            favoriteCharactersIdStorage.removeFromFavoriteIds(id)
        } else {
            favoriteCharactersIdStorage.addToFavoriteIds(id)
        }
    }
}
