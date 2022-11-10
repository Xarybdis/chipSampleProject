package com.example.android_chipsampleapp.ui.breed_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.android_chipsampleapp.R
import com.example.android_chipsampleapp.databinding.FragmentBreedDetailBinding
import com.example.android_chipsampleapp.network.models.Response
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class BreedDetailFragment : Fragment(R.layout.fragment_breed_detail), BreedDetailAdapter.OnItemClickListener {
    private var breedName = ""
    private var subBreedName = ""
    private val viewModel: BreedDetailViewModel by viewModel()
    private lateinit var breedDetailAdapter: BreedDetailAdapter
    private lateinit var binding: FragmentBreedDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBreedDetailBinding.bind(view)
        breedName = arguments?.getString("breedName").toString()
        subBreedName = arguments?.getString("subBreedName").toString()
        breedDetailAdapter = BreedDetailAdapter(requireContext(), this)

        setupViews()
        fetchImages(breedName, subBreedName)
    }

    private fun setupViews() {
        binding.apply {
            recyclerviewBreedDetail.apply {
                adapter = breedDetailAdapter
                setHasFixedSize(true)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.breedDetailEvent.collect { event ->
                when (event) {
                    is BreedDetailViewModel.BreedDetailEvent.NavigateToBreedStandaloneImageScreen -> {
                        val action = BreedDetailFragmentDirections.actionBreedDetailFragmentToStandAloneImageFragment(event.breedImageUrl)
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }

    private fun fetchImages(breedName: String, subBreedName: String) {
        viewModel.getImages(breedName, subBreedName).observe(viewLifecycleOwner) { it ->
            it?.let { response ->
                when (response) {
                    is Response.Success -> {
                        updateBreedImageRecyclerView(response.data)
                    }
                    is Response.Error -> {
                        Timber.d("An error occurred during fetchImages()")
                    }
                    is Response.Loading -> {
                        //TODO Loading will be implemented
                    }
                }
            }
        }
    }

    private fun updateBreedImageRecyclerView(data: List<String>?) {
        data?.let { breedDetailAdapter.updateBreedImageList(it) }
    }

    override fun breedImageItemClick(breedImageUrl: String) {
        viewModel.breedImageClicked(breedImageUrl)
    }

}