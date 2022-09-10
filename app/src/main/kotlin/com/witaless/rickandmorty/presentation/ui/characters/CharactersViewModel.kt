package com.witaless.rickandmorty.presentation.ui.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.witaless.rickandmorty.data.CharacterRepository
import com.witaless.rickandmorty.presentation.model.CharacterItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private val charactersFlow = MutableStateFlow<List<CharacterItem>>(emptyList())
    private val favoriteCharactersFlow = MutableStateFlow<List<CharacterItem>>(emptyList())
    private val progressFlow = MutableStateFlow(false)
    private val isFavoriteViewEnabledFlow = MutableStateFlow(false)
    private var isLastPage = false
    private var lastPageNumber = 1

    val characters: LiveData<List<CharacterItem>> =
        isFavoriteViewEnabledFlow.flatMapLatest { isFavorites ->
            if (isFavorites) {
                favoriteCharactersFlow
            } else {
                charactersFlow
            }
        }.asLiveData()

    val progress: LiveData<Boolean> = progressFlow.asLiveData()

    val isFavoriteViewEnabled: LiveData<Boolean> = isFavoriteViewEnabledFlow.asLiveData()

    init {
        loadMore()
    }

    fun loadMore() {
        if (isFavoriteViewEnabledFlow.value) {
            return
        }

        if (!isLastPage && !progressFlow.value) {
            progressFlow.value = true

            viewModelScope.launch {
                characterRepository.getCharacters(lastPageNumber)
                    .onSuccess { charactersPage ->
                        lastPageNumber++
                        isLastPage = charactersPage.isLastPage
                        progressFlow.value = false
                        updateCharactersList(charactersFlow.value + charactersPage.items)
                    }
                    .onFailure {
                        // TODO
                    }
            }
        }
    }

    fun showFavoritesClicked() {
        isFavoriteViewEnabledFlow.value = !isFavoriteViewEnabledFlow.value
        updateCurrentList()
    }

    fun toggleItemFavoriteState(id: Int) {
        viewModelScope.launch {
            characterRepository.toggleItemFavoriteState(id)
            updateCurrentList()
        }
    }

    private fun updateCurrentList() {
        if (isFavoriteViewEnabledFlow.value) {
            loadFavorites()
        } else {
            updateCharactersList(charactersFlow.value)
        }
    }

    private fun updateCharactersList(items: List<CharacterItem>) {
        viewModelScope.launch {
            val ids = characterRepository.getFavoriteCharacterIds().getOrDefault(emptyList())
            charactersFlow.value = items.setFavoriteStatus(ids = ids)
        }
    }

    private fun loadFavorites() {
        progressFlow.value = true

        viewModelScope.launch {
            characterRepository.getFavoriteCharacters()
                .onSuccess { items ->
                    progressFlow.value = false
                    favoriteCharactersFlow.value = items
                }
                .onFailure {
                    // TODO
                }
        }
    }

    private fun List<CharacterItem>.setFavoriteStatus(ids: List<Int>) = map { item ->
        item.copy(isFavorite = ids.contains(item.id))
    }
}
