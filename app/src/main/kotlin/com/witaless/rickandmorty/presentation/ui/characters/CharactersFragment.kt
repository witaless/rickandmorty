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

        val characterAdapter = CharacterRecyclerViewAdapter { id ->
            val action = CharactersFragmentDirections
                .actionCharactersFragmentToCharacterDetailsFragment(id)
            findNavController().navigate(action)
        }

        binding.rvCharacters.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = characterAdapter
        }

        viewModel.characters.observe(viewLifecycleOwner) {
            characterAdapter.submitList(it)
        }

        viewModel.progress.observe(viewLifecycleOwner) {
            binding.cpiProgress.isVisible = it
        }
    }
}
