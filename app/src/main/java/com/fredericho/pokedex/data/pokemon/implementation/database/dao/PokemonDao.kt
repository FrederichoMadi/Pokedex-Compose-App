package com.fredericho.pokedex.data.pokemon.implementation.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fredericho.pokedex.data.pokemon.implementation.database.model.PokemonModel
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon_tbl")
    fun getPokemon(): List<PokemonModel>

    @Query("SELECT * FROM pokemon_tbl WHERE name LIKE :name")
    fun searchPokemon(name: String): List<PokemonModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemonModel: PokemonModel)



}