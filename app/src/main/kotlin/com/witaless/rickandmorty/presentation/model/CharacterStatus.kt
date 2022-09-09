package com.witaless.rickandmorty.presentation.model

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.witaless.rickandmorty.R

enum class CharacterStatus(@StringRes val stringRes: Int, @ColorRes val colorRes: Int) {

    ALIVE(stringRes = R.string.status_alive, colorRes = R.color.status_alive),
    DEAD(stringRes = R.string.status_dead, colorRes = R.color.status_dead),
    UNKNOWN(stringRes = R.string.status_unknown, colorRes = R.color.status_unknown)
}