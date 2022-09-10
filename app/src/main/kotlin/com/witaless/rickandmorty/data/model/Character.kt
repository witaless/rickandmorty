package com.witaless.rickandmorty.data.model

import com.witaless.rickandmorty.presentation.model.CharacterGender
import com.witaless.rickandmorty.presentation.model.CharacterItem
import com.witaless.rickandmorty.presentation.model.CharacterStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Character(
    val id: Int,
    val name: String,
    val status: Status,
    val species: String,
    val type: String,
    val gender: Gender,
    val origin: Location,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
) {

    @Serializable
    enum class Gender {
        @SerialName("Female")
        FEMALE,

        @SerialName("Male")
        MALE,

        @SerialName("Genderless")
        GENDERLESS,

        @SerialName("unknown")
        UNKNOWN
    }

    @Serializable
    enum class Status {
        @SerialName("Alive")
        ALIVE,

        @SerialName("Dead")
        DEAD,

        @SerialName("unknown")
        UNKNOWN
    }
}

fun Character.toItem(isFavorite: Boolean = false) = CharacterItem(
    id = id,
    name = name,
    species = species,
    imageUrl = image,
    status = status.toUi(),
    gender = gender.toUi(),
    isFavorite = isFavorite
)

fun Character.Gender.toUi() = when (this) {
    Character.Gender.FEMALE -> CharacterGender.FEMALE
    Character.Gender.MALE -> CharacterGender.MALE
    Character.Gender.GENDERLESS -> CharacterGender.GENDERLESS
    Character.Gender.UNKNOWN -> CharacterGender.UNKNOWN
}

fun Character.Status.toUi() = when (this) {
    Character.Status.ALIVE -> CharacterStatus.ALIVE
    Character.Status.DEAD -> CharacterStatus.DEAD
    Character.Status.UNKNOWN -> CharacterStatus.UNKNOWN
}
