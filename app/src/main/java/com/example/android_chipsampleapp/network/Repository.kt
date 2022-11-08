package com.example.android_chipsampleapp.network

import com.example.android_chipsampleapp.network.models.BreedListModel
import com.example.android_chipsampleapp.network.models.BreedModel

class Repository(private val api: Api) {

    suspend fun fetchBreedsList(): List<BreedListModel> = api.listAllBreeds()

    suspend fun fetchImagesByBreed(breedType: String): List<BreedModel> = api.listImagesByBreed(breedType)

    suspend fun fetchImagesBySubBreed(breedType: String, subBreedType: String): List<BreedModel> = api.listImagesBySubBreed(breedType, subBreedType)

    suspend fun fetchRandomBreedImage(): BreedModel = api.getRandomImage()
}