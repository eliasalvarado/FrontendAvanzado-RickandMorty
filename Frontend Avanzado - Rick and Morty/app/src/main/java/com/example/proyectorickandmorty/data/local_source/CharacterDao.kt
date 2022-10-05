package com.example.proyectorickandmorty.data.local_source

import androidx.room.*
import com.example.proyectorickandmorty.data.local_source.model.Character


@Dao
interface CharacterDao {

    @Query("SELECT * FROM Character")
    suspend fun getCharacters(): List<Character>

    @Query("SELECT * FROM Character WHERE id = :id")
    suspend fun getCharacterById(id: Int): Character?

    @Update
    suspend fun update(character: Character)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: Character)

    @Delete
    suspend fun delete(character: Character): Int

    @Query("DELETE FROM Character")
    suspend fun deleteAll(): Int

}