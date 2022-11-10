package com.example.android_chipsampleapp.network

import com.example.android_chipsampleapp.network.models.BaseModel
import com.example.android_chipsampleapp.network.models.BreedListModel
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("breeds/list/all")
    suspend fun listAllBreeds(): BreedListModel

    @GET("breed/{breedType}/images")
    suspend fun listImagesByBreed(@Path("breedType") breedType: String): BaseModel<List<String>>

    @GET("breed/{breedType}/{subBreedType}/images")
    suspend fun listImagesBySubBreed(@Path("breedType") breedType: String, @Path("subBreedType") subBreedType: String): BaseModel<List<String>>

    @GET("breeds/image/random")
    suspend fun getRandomImage(): BaseModel<String>
}