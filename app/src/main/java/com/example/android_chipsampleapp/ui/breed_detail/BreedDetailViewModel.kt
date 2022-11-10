package com.example.android_chipsampleapp.ui.breed_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.android_chipsampleapp.network.Repository
import com.example.android_chipsampleapp.network.models.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class BreedDetailViewModel(private val repository: Repository, val savedState: SavedStateHandle) : ViewModel() {


    fun getImages(breedName: String, subBreedName: String) = liveData(Dispatchers.IO) {
        emit(Response.Loading(data = null))
        try {
            val data = if (subBreedName.isEmpty()) repository.fetchImagesByBreed(breedName) else repository.fetchImagesBySubBreed(breedName, subBreedName)
            emit(Response.Success(data = data.data, data.status))
        } catch (exception: Exception) {
            emit(Response.Error(data = null, status = exception.localizedMessage ?: "Error occurred."))
        }
    }

    fun breedImageClicked(breedImageUrl: String) = viewModelScope.launch {
        breedDetailEventChannel.send(BreedDetailEvent.NavigateToBreedStandaloneImageScreen(breedImageUrl))
    }

    private val breedDetailEventChannel = Channel<BreedDetailEvent>()
    val breedDetailEvent = breedDetailEventChannel.receiveAsFlow()

    sealed class BreedDetailEvent {
        data class NavigateToBreedStandaloneImageScreen(val breedImageUrl: String) : BreedDetailEvent()
    }
}