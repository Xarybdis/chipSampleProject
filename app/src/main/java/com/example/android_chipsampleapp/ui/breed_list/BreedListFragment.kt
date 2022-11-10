package com.example.android_chipsampleapp.ui.breed_list

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_chipsampleapp.R
import com.example.android_chipsampleapp.databinding.FragmentBreedListBinding
import com.example.android_chipsampleapp.network.models.Response
import com.example.android_chipsampleapp.utils.onQueryTextChanged
import com.example.android_chipsampleapp.utils.stateProgressLoading
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class BreedListFragment : Fragment(R.layout.fragment_breed_list), BreedListAdapter.OnItemClickListener {

    private lateinit var binding: FragmentBreedListBinding
    private val viewModel: BreedListViewModel by viewModel()
    private lateinit var searchView: SearchView
    private var breedNameList: List<Pair<String, List<String>>>? = listOf()
    private lateinit var breedListAdapter: BreedListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBreedListBinding.bind(view)
        breedListAdapter = BreedListAdapter(this@BreedListFragment)
        setupViewsAndCalls()
        fetchBreedsList()

        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_breed_list, menu)
        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView

        searchView.onQueryTextChanged { searchValue ->
            breedNameList?.filter { it ->
                it.first.contains(searchValue) || it.second.contains(searchValue)
            }?.let { it1 ->
                breedListAdapter.updateBreedList(it1)
            }
        }
    }

    private fun setupViewsAndCalls() {
        binding.apply {
            recyclerviewList.apply {
                adapter = breedListAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            fabRandomPhoto.setOnClickListener {
                fetchRandomImage()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.breedEvent.collect { event ->
                when (event) {
                    is BreedListViewModel.BreedListEvent.NavigateToBreedDetailScreen -> {
                        val action =
                            BreedListFragmentDirections.actionBreedListFragmentToBreedDetailFragment(
                                event.breedName,
                                event.subBreedName,
                                if (event.subBreedName.isNotEmpty()) "${event.breedName.replaceFirstChar { it.uppercaseChar() }} > ${event.subBreedName.replaceFirstChar { it.uppercaseChar() }} "
                                else event.breedName.replaceFirstChar { it.uppercaseChar() })
                        findNavController().navigate(action)
                    }
                    is BreedListViewModel.BreedListEvent.NavigateToBreedStandaloneImageScreen -> {
                        val action = BreedListFragmentDirections.actionBreedListFragmentToStandAloneImageFragment(event.breedImageUrl)
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }

    private fun fetchRandomImage() {
        viewModel.onRandomPhotoButtonClicked().observe(viewLifecycleOwner) {
            it.let { response ->
                when (response) {
                    is Response.Success -> {
                        stateProgressLoading(false, binding.loadingView.root, binding.recyclerviewList)
                        response.data?.let { imageUrl -> viewModel.continueStandAloneImageScreen(imageUrl) }
                    }
                    is Response.Error -> {
                        Timber.d("An error occurred during fetchBreedsList()")
                        Snackbar.make(requireView(), "An error occurred. Check your internet.", Snackbar.LENGTH_INDEFINITE).setAction("TRY AGAIN") {
                            fetchRandomImage()
                        }.show()
                    }
                    is Response.Loading -> {
                        stateProgressLoading(true, binding.loadingView.root, binding.recyclerviewList)
                    }
                }
            }
        }
    }


    private fun fetchBreedsList() {
        viewModel.getListOfBreeds().observe(viewLifecycleOwner) {
            it?.let { response ->
                when (response) {
                    is Response.Success -> {
                        stateProgressLoading(false, binding.loadingView.root, binding.recyclerviewList)
                        updateBreedListRecyclerView(response.data)
                        breedNameList = response.data
                    }
                    is Response.Error -> {
                        Timber.d("An error occurred during fetchBreedsList()")
                        Snackbar.make(requireView(), "An error occurred. Check your internet.", Snackbar.LENGTH_INDEFINITE).setAction("TRY AGAIN") {
                            fetchBreedsList()
                        }.show()
                    }
                    is Response.Loading -> {
                        stateProgressLoading(true, binding.loadingView.root, binding.recyclerviewList)
                    }
                }
            }
        }
    }

    private fun updateBreedListRecyclerView(data: List<Pair<String, List<String>>>?) {
        data?.let { breedListAdapter.updateBreedList(it) }
    }

    override fun breedItemClickListener(breedName: String) {
        viewModel.breedItemClicked(breedName)
    }

    override fun subItemClickListener(breedName: String, subBreedName: String) {
        viewModel.breedSubItemClicked(breedName, subBreedName)
    }
}