package com.example.proyectorickandmorty.datasource.model

import com.google.gson.annotations.SerializedName

data class AllApiResponse(
    val info: Info,
    @SerializedName("results") val characters: List<Character>
)