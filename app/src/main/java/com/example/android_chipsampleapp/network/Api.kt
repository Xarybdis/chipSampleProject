package com.example.android_chipsampleapp.network

import com.example.android_chipsampleapp.network.models.BreedImagesModel
import com.example.android_chipsampleapp.network.models.BreedListModel
import com.example.android_chipsampleapp.network.models.RandomBreedModel
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("breeds/list/all")
    suspend fun listAllBreeds(): BreedListModel

    @GET("breed/{breedType}/images")
    suspend fun listImagesByBreed(@Path("breedType") breedType: String): BreedImagesModel

    @GET("breed/{breedType}/{subBreedType}/images")
    suspend fun listImagesBySubBreed(@Path("breedType") breedType: String, @Path("subBreedType") subBreedType: String): BreedImagesModel

    @GET("breeds/images/random")
    suspend fun getRandomImage(): RandomBreedModel
}