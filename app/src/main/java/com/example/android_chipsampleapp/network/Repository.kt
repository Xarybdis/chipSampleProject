package com.example.android_chipsampleapp.network

import com.example.android_chipsampleapp.network.models.BaseModel
import com.example.android_chipsampleapp.network.models.BreedListModel
import com.example.android_chipsampleapp.network.models.RandomBreedModel

class Repository(private val api: Api) {

    suspend fun fetchBreedsList(): BreedListModel = api.listAllBreeds()

    suspend fun fetchImagesByBreed(breedName: String): BaseModel<List<String>> = api.listImagesByBreed(breedName)

    suspend fun fetchImagesBySubBreed(breedName: String, subBreedName: String): BaseModel<List<String>> = api.listImagesBySubBreed(breedName, subBreedName)

    suspend fun fetchRandomBreedImage(): RandomBreedModel = api.getRandomImage()
}