package com.fredericho.pokedex.data.pokemon.repository

import androidx.paging.PagingData
import com.fredericho.pokedex.data.pokemon.implementation.database.model.PokemonModel
import com.fredericho.pokedex.data.pokemon.implementation.remote.response.DetailResponse
import com.fredericho.pokedex.utils.Resource
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    suspend fun getPokemon() : Flow<PagingData<PokemonModel>>
    suspend fun getPokemonByName(name: String) :Flow<Resource<DetailResponse>>
    suspend fun getPokemonOffline() :List<PokemonModel>
    suspend fun searchPokemon(name: String) :List<PokemonModel>

}