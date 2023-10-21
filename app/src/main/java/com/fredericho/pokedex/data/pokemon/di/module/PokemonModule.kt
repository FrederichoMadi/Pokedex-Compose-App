package com.fredericho.pokedex.data.pokemon.di.module

import com.fredericho.pokedex.data.pokemon.implementation.database.dao.PokemonDao
import com.fredericho.pokedex.data.pokemon.implementation.remote.api.PokemonApi
import com.fredericho.pokedex.data.pokemon.repository.PokemonRepository
import com.fredericho.pokedex.data.pokemon.repository.PokemonRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokemonModule {

    @Provides
    @Singleton
    fun providePokemonApi(retrofit: Retrofit): PokemonApi =
        retrofit.create(PokemonApi::class.java)


    @Provides
    @Singleton
    fun providePokemonRepository(
        pokemonApi: PokemonApi,
        pokemonDao: PokemonDao
    ): PokemonRepository {
        return PokemonRepositoryImpl(
            pokemonApi,
            pokemonDao,
        )
    }

}