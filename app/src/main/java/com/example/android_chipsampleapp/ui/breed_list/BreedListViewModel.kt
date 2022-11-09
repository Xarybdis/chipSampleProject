package com.example.android_chipsampleapp.ui.breed_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.android_chipsampleapp.network.Repository
import com.example.android_chipsampleapp.network.models.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class BreedListViewModel(private val repository: Repository) : ViewModel() {


    fun getListOfBreeds() = liveData(Dispatchers.IO) {
        emit(Response.Loading(data = null))
        try {
            val listOfBreeds = repository.fetchBreedsList()
            val data = convertedPairListFromMap(listOfBreeds.breedType)
            emit(Response.Success(data = data, status = listOfBreeds.status))
        } catch (exception: Exception) {
            emit(Response.Error(data = null, status = exception.localizedMessage ?: "Error occured."))
        }
    }

    private fun convertedPairListFromMap(map: Map<String, List<String>>): List<Pair<String, List<String>>> = map.toList().map { Pair(it.first, it.second) }

    fun breedItemClicked(breedName: String) = viewModelScope.launch {
        breedEventChannel.send(BreedListEvent.NavigateToBreedDetailScreen(breedName))
    }

    fun breedSubItemClicked(subBreedName: String) = viewModelScope.launch {
        breedEventChannel.send(BreedListEvent.NavigateToBreedDetailScreen(subBreedName))
    }

    private val breedEventChannel = Channel<BreedListEvent>()
    val breedEvent = breedEventChannel.receiveAsFlow()

    sealed class BreedListEvent {
        data class NavigateToBreedDetailScreen(val breedName: String) : BreedListEvent()
    }
}