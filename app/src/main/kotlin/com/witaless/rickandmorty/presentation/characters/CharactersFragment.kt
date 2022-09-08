package com.witaless.rickandmorty.presentation.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.witaless.rickandmorty.databinding.FragmentCharactersListBinding
import com.witaless.rickandmorty.presentation.characters.placeholder.PlaceholderContent

class CharactersFragment : Fragment() {

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

        binding.rvCharacters.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CharacterRecyclerViewAdapter(PlaceholderContent.ITEMS) { id ->
                val action = CharactersFragmentDirections
                    .actionCharactersFragmentToCharacterDetailsFragment(id)
                findNavController().navigate(action)
            }
        }
    }
}
