package com.witaless.rickandmorty.presentation.ui.characterdetails

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.witaless.rickandmorty.R
import com.witaless.rickandmorty.databinding.FragmentCharacterDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment() {

    private val viewModel: CharacterDetailsViewModel by viewModels()
    private val args: CharacterDetailsFragmentArgs by navArgs()

    private lateinit var binding: FragmentCharacterDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ibBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.ibFavoriteStatus.setOnClickListener {
            viewModel.toggleFavoriteState()
        }

        viewModel.loadDetails(args.id)

        viewModel.character.observe(viewLifecycleOwner) {
            binding.tvName.text = it.name
            binding.tvDescription.text = it.description
            binding.tvOriginLocation.text = it.originLocation
            binding.tvLastKnownLocation.text = it.lastKnownLocation

            binding.chipStatus.setText(it.status.stringRes)
            binding.chipStatus.setChipBackgroundColorResource(it.status.colorRes)
            binding.chipGender.setText(it.gender.stringRes)
            binding.chipGender.setChipBackgroundColorResource(it.gender.colorRes)

            binding.ivImage.load(it.imageUrl) {
                placeholder(ColorDrawable(requireContext().getColor(R.color.placeholder)))
                crossfade(true)
            }
        }

        viewModel.progress.observe(viewLifecycleOwner) {
            binding.cpiProgress.isVisible = it
        }

        viewModel.isFavorite.observe(viewLifecycleOwner) {
            binding.ibFavoriteStatus.setImageResource(
                if (it) {
                    R.drawable.ic_star_filled
                } else {
                    R.drawable.ic_star_outline
                }
            )
        }

        viewModel.showError.observe(viewLifecycleOwner) {
            binding.tvError.isVisible = it
        }

        viewModel.showContent.observe(viewLifecycleOwner) {
            binding.clContent.isVisible = it
        }
    }
}
