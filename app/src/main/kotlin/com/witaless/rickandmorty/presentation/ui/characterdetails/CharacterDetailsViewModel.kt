package com.witaless.rickandmorty.presentation.ui.characterdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.witaless.rickandmorty.data.CharacterRepository
import com.witaless.rickandmorty.presentation.model.CharacterDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private val characterFlow = MutableStateFlow<CharacterDetails?>(null)
    private val progressFlow = MutableStateFlow(true)
    private val isFavoriteFlow = MutableStateFlow(false)
    private val showContentFlow = MutableStateFlow(false)
    private val showErrorFlow = MutableStateFlow(false)

    val character: LiveData<CharacterDetails> = characterFlow.filterNotNull().asLiveData()
    val progress: LiveData<Boolean> = progressFlow.asLiveData()
    val isFavorite: LiveData<Boolean> = isFavoriteFlow.asLiveData()
    val showContent: LiveData<Boolean> = showContentFlow.asLiveData()
    val showError: LiveData<Boolean> = showErrorFlow.asLiveData()

    init {
        viewModelScope.launch {
            characterRepository.getFavoriteCharacterIdsFlow().collectLatest { ids ->
                val id = characterFlow.filterNotNull().first().id
                isFavoriteFlow.value = ids.contains(id)
            }
        }
    }

    fun loadDetails(id: Int) {
        viewModelScope.launch {
            characterRepository.getCharacter(id)
                .onSuccess { details ->
                    progressFlow.value = false
                    characterFlow.value = details
                    showContentFlow.value = true
                }
                .onFailure {
                    progressFlow.value = false
                    showContentFlow.value = false
                    showErrorFlow.value = true
                }
        }
    }

    fun toggleFavoriteState() {
        viewModelScope.launch {
            characterRepository.toggleCharacterFavoriteState(
                characterFlow.value?.id ?: return@launch
            )
        }
    }
}
