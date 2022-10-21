package com.example.proyectorickandmorty.datasource.model

data class Character(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)

fun Character.mapToModel(): com.example.proyectorickandmorty.datasource.model.Character = Character(
    id = id.toInt(),
    name = name,
    status = status,
    species = species,
    gender = gender,
    image = image,
    origin = origin,
    episode = episode
)