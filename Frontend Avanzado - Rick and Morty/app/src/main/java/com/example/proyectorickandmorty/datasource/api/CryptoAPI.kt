package com.example.proyectorickandmorty.datasource.api

import com.example.proyectorickandmorty.datasource.model.AllApiResponse
import com.example.proyectorickandmorty.datasource.model.Character
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CryptoAPI {

    @GET("/api/character")
    fun getCrypto():AllApiResponse

    @GET("/api/character/{id}")
    fun getCharacter(
        @Path("id") id: Int
    ): Character
}