package com.witaless.rickandmorty.presentation.model

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.witaless.rickandmorty.R

enum class CharacterGender(@StringRes val stringRes: Int, @ColorRes val colorRes: Int) {

    FEMALE(stringRes = R.string.gender_female, colorRes = R.color.gender_female),
    MALE(stringRes = R.string.gender_male, colorRes = R.color.gender_male),
    GENDERLESS(stringRes = R.string.gender_genderless, colorRes = R.color.gender_genderless),
    UNKNOWN(stringRes = R.string.gender_unknown, colorRes = R.color.gender_unknown)
}
