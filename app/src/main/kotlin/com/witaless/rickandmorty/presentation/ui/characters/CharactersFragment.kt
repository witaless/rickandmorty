package com.witaless.rickandmorty.presentation.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.witaless.rickandmorty.R
import com.witaless.rickandmorty.databinding.FragmentCharactersListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment : Fragment() {

    private val viewModel: CharactersViewModel by viewModels()

    private lateinit var binding: FragmentCharactersListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val characterAdapter = CharacterRecyclerViewAdapter(
            onItemClick = { id ->
                val action = CharactersFragmentDirections
                    .actionCharactersFragmentToCharacterDetailsFragment(id)
                findNavController().navigate(action)
            },
            onToggleFavoriteClick = { id ->
                viewModel.toggleItemFavoriteState(id)
            }
        )

        binding.rvCharacters.apply {
            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager
            adapter = characterAdapter
            addOnScrollListener(
                PaginationScrollListener(linearLayoutManager) {
                    viewModel.loadMore()
                }
            )
        }

        binding.ibShowFavorites.setOnClickListener {
            viewModel.showFavoritesClicked()
        }

        viewModel.characters.observe(viewLifecycleOwner) {
            characterAdapter.submitList(it)
        }

        viewModel.progress.observe(viewLifecycleOwner) {
            binding.cpiProgress.isVisible = it
        }

        viewModel.isFavoriteViewEnabled.observe(viewLifecycleOwner) {
            binding.ibShowFavorites.setImageResource(
                if (it) {
                    R.drawable.ic_star_filled
                } else {
                    R.drawable.ic_star_outline
                }
            )
        }
    }
}
