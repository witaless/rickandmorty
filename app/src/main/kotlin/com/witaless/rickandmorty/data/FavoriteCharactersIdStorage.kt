package com.witaless.rickandmorty.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteCharactersIdStorage @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    fun getFavoriteIds(): Flow<List<Int>> {
        return dataStore.data.map { preferences ->
            val favoriteDds = preferences[FAVORITE_IDS] ?: emptySet()
            favoriteDds.map { it.toInt() }
        }
    }

    suspend fun addToFavoriteIds(id: Int) {
        dataStore.edit { preferences ->
            val favoriteDds = preferences[FAVORITE_IDS] ?: emptySet()
            preferences[FAVORITE_IDS] = favoriteDds.toMutableSet().apply {
                add(id.toString())
            }
        }
    }

    suspend fun removeFromFavoriteIds(id: Int) {
        dataStore.edit { preferences ->
            val favoriteDds = preferences[FAVORITE_IDS] ?: emptySet()
            preferences[FAVORITE_IDS] = favoriteDds.toMutableSet().apply {
                remove(id.toString())
            }
        }
    }

    companion object {

        private val FAVORITE_IDS = stringSetPreferencesKey("FAVORITE_IDS")
    }
}