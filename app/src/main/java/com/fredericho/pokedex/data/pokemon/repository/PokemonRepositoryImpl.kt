package com.fredericho.pokedex.data.pokemon.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fredericho.pokedex.data.pokemon.implementation.database.dao.PokemonDao
import com.fredericho.pokedex.data.pokemon.implementation.database.model.PokemonModel
import com.fredericho.pokedex.data.pokemon.implementation.paging.PokemonPagingSource
import com.fredericho.pokedex.data.pokemon.implementation.remote.api.PokemonApi
import com.fredericho.pokedex.data.pokemon.implementation.remote.response.DetailResponse
import com.fredericho.pokedex.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PokemonRepositoryImpl(
    private val pokemonApi: PokemonApi,
    private val pokemonDao: PokemonDao
) : PokemonRepository {
    override suspend fun getPokemon(): Flow<PagingData<PokemonModel>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                PokemonPagingSource(pokemonApi, pokemonDao)
            }
        ).flow
    }

    override suspend fun getPokemonByName(name: String): Flow<Resource<DetailResponse>> {
        return flow {
            try {
                val response = pokemonApi.getPokemonDetail(name)
                if (response.isSuccessful) {
                    emit(Resource.Success(response.body()!!))
                } else {
                    emit(Resource.Error(response.message()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getPokemonOffline(): List<PokemonModel> {
        return pokemonDao.getPokemon()
    }

    override suspend fun searchPokemon(name: String): List<PokemonModel> {
        return pokemonDao.searchPokemon(name)
    }
}