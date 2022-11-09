package com.example.android_chipsampleapp.network

import com.example.android_chipsampleapp.network.models.BreedImagesModel
import com.example.android_chipsampleapp.network.models.BreedListModel
import com.example.android_chipsampleapp.network.models.RandomBreedModel

class Repository(private val api: Api) {

    suspend fun fetchBreedsList(): BreedListModel = api.listAllBreeds()

    suspend fun fetchImagesByBreed(breedType: String): BreedImagesModel = api.listImagesByBreed(breedType)

    suspend fun fetchImagesBySubBreed(breedType: String, subBreedType: String): BreedImagesModel = api.listImagesBySubBreed(breedType, subBreedType)

    suspend fun fetchRandomBreedImage(): RandomBreedModel = api.getRandomImage()
}