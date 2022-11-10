package com.example.android_chipsampleapp.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BaseModel<T>(
    @SerializedName("message")
    val data: T,
    val status: String
)

data class BreedListModel(
    @SerializedName("message")
    @Expose
    val breedType: Map<String, List<String>>,
    val status: String
)
