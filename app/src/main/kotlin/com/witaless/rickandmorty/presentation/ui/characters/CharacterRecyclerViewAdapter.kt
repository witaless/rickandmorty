package com.witaless.rickandmorty.presentation.ui.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.chip.Chip
import com.witaless.rickandmorty.R
import com.witaless.rickandmorty.databinding.ItemCharacterBinding
import com.witaless.rickandmorty.presentation.model.CharacterItem

class CharacterRecyclerViewAdapter(
    private val onItemClick: (Int) -> Unit,
    private val onToggleFavoriteClick: (Int) -> Unit
) : ListAdapter<CharacterItem, CharacterRecyclerViewAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {
        private val clRoot: ConstraintLayout = binding.clRoot
        private val tvName: TextView = binding.tvName
        private val tvDescription: TextView = binding.tvDescription
        private val ivImage: ImageView = binding.ivImage
        private val ibFavorite: ImageButton = binding.ibFavorite
        private val chipStatus: Chip = binding.chipStatus
        private val chipGender: Chip = binding.chipGender

        fun bind(item: CharacterItem) {
            tvName.text = item.name
            tvDescription.text = item.species

            chipStatus.setText(item.status.stringRes)
            chipStatus.setChipBackgroundColorResource(item.status.colorRes)
            chipGender.setText(item.gender.stringRes)
            chipGender.setChipBackgroundColorResource(item.gender.colorRes)

            ibFavorite.setImageResource(
                if (item.isFavorite) {
                    R.drawable.ic_star_filled
                } else {
                    R.drawable.ic_star_outline
                }
            )

            ivImage.load(item.imageUrl) {
                placeholder(R.drawable.ic_placeholder_avatar)
                transformations(CircleCropTransformation())
                crossfade(true)
            }

            clRoot.setOnClickListener {
                onItemClick(item.id)
            }

            ibFavorite.setOnClickListener {
                onToggleFavoriteClick(item.id)
            }
        }
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CharacterItem>() {
            override fun areItemsTheSame(
                oldItem: CharacterItem,
                newItem: CharacterItem
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: CharacterItem,
                newItem: CharacterItem
            ): Boolean = oldItem == newItem
        }
    }
}
