package com.example.proyectorickandmorty.data.local_source

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.proyectorickandmorty.data.local_source.model.Character


@Dao
interface CharacterDao {

    @Query("SELECT * FROM Character")
    suspend fun getCharacters(): List<Character>

    @Query("SELECT * FROM Character WHERE id = :id")
    suspend fun getCharacterById(id: Int): Character?

    @Update
    suspend fun update(character: Character)

    @Delete
    suspend fun delete(character: Character): Int

    @Query("DELETE FROM Character")
    suspend fun deleteAll(): Int

}