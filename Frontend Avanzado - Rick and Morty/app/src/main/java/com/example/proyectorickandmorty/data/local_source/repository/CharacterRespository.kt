package com.example.proyectorickandmorty.data.local_source.repository

import com.example.proyectorickandmorty.datasource.util.DataState
import com.example.proyectorickandmorty.data.local_source.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRespository {
    fun getAllCharacters(): Flow<DataState<List<Character>>>
    fun deleteAllCharacters()
    fun getCharacter(id: Int): Flow<DataState<Character>?>
    fun updateCharacter(character: Character)
    fun deleteCharacter(id: Int)
}