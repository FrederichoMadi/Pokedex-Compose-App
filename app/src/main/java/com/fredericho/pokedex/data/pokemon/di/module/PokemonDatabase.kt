package com.fredericho.pokedex.data.pokemon.di.module

import android.content.Context
import androidx.room.Room
import com.fredericho.pokedex.data.pokemon.implementation.database.DatabaseContract
import com.fredericho.pokedex.data.pokemon.implementation.database.dao.PokemonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokemonDatabase {

    @Provides
    @Singleton
    fun providePokemonDao(pokemonDatabase: DatabaseContract) : PokemonDao =
        pokemonDatabase.pokemonDao()

    @Provides
    @Singleton
    fun providePokemonDatabase(@ApplicationContext context: Context): DatabaseContract =
        Room.databaseBuilder(
            context,
            DatabaseContract::class.java,
            "pokemon_db"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
}