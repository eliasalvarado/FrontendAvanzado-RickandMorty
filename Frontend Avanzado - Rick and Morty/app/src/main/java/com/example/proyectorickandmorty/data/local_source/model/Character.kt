package com.example.proyectorickandmorty.data.local_source.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Character(
    val episode: Int,
    val gender: String,
    @PrimaryKey
    val id: Int,
    val image: String,
    val location: String,
    val name: String,
    val origin: String,
    val species: String,
    val status: String,
    val type: String,
)
