package com.example.android_chipsampleapp.ui.breed_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_chipsampleapp.R
import com.example.android_chipsampleapp.databinding.FragmentBreedListBinding
import com.example.android_chipsampleapp.network.models.Response
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class BreedListFragment : Fragment(R.layout.fragment_breed_list), BreedListAdapter.OnItemClickListener {

    private val viewModel: BreedListViewModel by viewModel()
    private lateinit var breedListAdapter: BreedListAdapter
    private lateinit var binding: FragmentBreedListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBreedListBinding.bind(view)
        breedListAdapter = BreedListAdapter(this@BreedListFragment)
        setupViewsAndCalls()
        fetchBreedsList()
    }

    private fun setupViewsAndCalls() {
        binding.apply {
            recyclerviewList.apply {
                adapter = breedListAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
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
                }
            }
        }
    }


    private fun fetchBreedsList() {
        viewModel.getListOfBreeds().observe(viewLifecycleOwner) {
            it?.let { response ->
                when (response) {
                    is Response.Success -> {
                        updateBreedListRecyclerView(response.data)
                    }
                    is Response.Error -> {
                        Timber.d("An error occurred during fetchBreedsList()")
                    }
                    is Response.Loading -> Timber.d("ITS LOADINGGGGG")
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