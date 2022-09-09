package com.witaless.rickandmorty.presentation.ui.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.witaless.rickandmorty.data.CharacterRepository
import com.witaless.rickandmorty.presentation.model.CharacterItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private val charactersLiveData = MutableLiveData<List<CharacterItem>>()
    private val progressLiveData = MutableLiveData<Boolean>()

    val characters: LiveData<List<CharacterItem>>
        get() = charactersLiveData

    val progress: LiveData<Boolean>
        get() = progressLiveData

    init {
        progressLiveData.value = true
        viewModelScope.launch {
            val result = characterRepository.getCharacters()
            result.onSuccess {
                progressLiveData.postValue(false)
                charactersLiveData.postValue(it)
            }
        }
    }
}