package com.example.android_chipsampleapp.ui.breed_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.android_chipsampleapp.network.Repository
import com.example.android_chipsampleapp.network.models.Response
import kotlinx.coroutines.Dispatchers

class BreedDetailViewModel(private val repository: Repository, val savedState: SavedStateHandle) : ViewModel() {


    fun getImages(breedName: String, subBreedName: String) = liveData(Dispatchers.IO) {
        emit(Response.Loading(data = null))
        try {
            val data = if (subBreedName.isEmpty()) repository.fetchImagesByBreed(breedName) else repository.fetchImagesBySubBreed(breedName, subBreedName)
            emit(Response.Success(data = data.message, data.status))
        } catch (exception: Exception) {
            emit(Response.Error(data = null, status = exception.localizedMessage ?: "Error occurred."))
        }
    }


}