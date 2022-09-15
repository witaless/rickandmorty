package com.witaless.rickandmorty.presentation.ui.characters.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.witaless.rickandmorty.R
import com.witaless.rickandmorty.presentation.model.CharacterItem
import com.witaless.rickandmorty.presentation.ui.widget.ColoredBadge
import com.witaless.rickandmorty.presentation.ui.widget.FavoriteButton

@Composable
fun CharacterCard(
    modifier: Modifier = Modifier,
    character: CharacterItem,
    onItemClick: (Int) -> Unit,
    onToggleFavoriteClick: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .then(modifier)
    ) {
        Card(shape = RoundedCornerShape(12.dp)) {
            Row(
                Modifier
                    .clickable { onItemClick(character.id) }
                    .fillMaxWidth()
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 16.dp, start = 16.dp)
                        .weight(1f)
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(color = colorResource(id = R.color.placeholder))
                            .size(80.dp),
                        model = character.imageUrl,
                        contentDescription = character.name
                    )
                    Spacer(Modifier.size(16.dp))
                    Column(Modifier.weight(1f)) {
                        Text(text = character.name, style = MaterialTheme.typography.h6)
                        Text(text = character.species, style = MaterialTheme.typography.caption)
                        Spacer(Modifier.size(4.dp))
                        Row {
                            ColoredBadge(
                                stringRes = character.status.stringRes,
                                colorRes = character.status.colorRes
                            )
                            Spacer(Modifier.size(8.dp))
                            ColoredBadge(
                                stringRes = character.gender.stringRes,
                                colorRes = character.gender.colorRes
                            )
                        }
                    }
                }
                Box(modifier = Modifier.padding(8.dp)) {
                    FavoriteButton(
                        onClick = { onToggleFavoriteClick(character.id) },
                        favorite = character.isFavorite
                    )
                }
            }
        }
    }
}