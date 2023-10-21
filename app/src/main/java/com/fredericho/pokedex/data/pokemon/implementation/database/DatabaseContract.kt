package com.fredericho.pokedex.data.pokemon.implementation.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fredericho.pokedex.data.pokemon.implementation.database.dao.PokemonDao
import com.fredericho.pokedex.data.pokemon.implementation.database.model.PokemonModel

@Database(entities = [PokemonModel::class], version = 1, exportSchema = false)
abstract class DatabaseContract : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}