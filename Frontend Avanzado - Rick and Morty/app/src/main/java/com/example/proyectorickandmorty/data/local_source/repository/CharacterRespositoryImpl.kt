package com.example.proyectorickandmorty.data.local_source.repository

import com.example.proyectorickandmorty.data.local_source.CharacterDao
import com.example.proyectorickandmorty.data.local_source.model.Character
import com.example.proyectorickandmorty.datasource.api.CryptoAPI
import com.example.proyectorickandmorty.datasource.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CharacterRespositoryImpl(
    private val characterDao: CharacterDao,
    private val api: CryptoAPI
): CharacterRespository {
    override fun getAllCharacters(): Flow<DataState<List<Character>>> = flow {
        emit(DataState.Loading)
        val localCharacters = characterDao.getCharacters()
        if(localCharacters.isEmpty()) {
            try {
                val remoteCharacters = api.getCrypto().characters
                val characctersToStore = remoteCharacters.map { dto -> dto.mapToModel }
            }
        }
    }

    override fun deleteAllCharacters() {
        TODO("Not yet implemented")
    }

    override fun getCharacter(id: Int): Flow<DataState<Character>?> {
        TODO("Not yet implemented")
    }

    override fun updateCharacter(character: Character) {
        TODO("Not yet implemented")
    }

    override fun deleteCharacter(id: Int) {
        TODO("Not yet implemented")
    }
}