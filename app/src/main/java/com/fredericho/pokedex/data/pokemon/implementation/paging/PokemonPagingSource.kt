package com.fredericho.pokedex.data.pokemon.implementation.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fredericho.pokedex.data.pokemon.implementation.database.dao.PokemonDao
import com.fredericho.pokedex.data.pokemon.implementation.database.model.PokemonModel
import com.fredericho.pokedex.data.pokemon.implementation.remote.api.PokemonApi
import com.fredericho.pokedex.data.pokemon.implementation.mapping.toPokemonModel

class PokemonPagingSource(
    private val pokemonApi: PokemonApi,
    private val pokemonDao: PokemonDao,
) : PagingSource<Int, PokemonModel>() {
    override fun getRefreshKey(state: PagingState<Int, PokemonModel>): Int? =
        state.anchorPosition?.let { anchorPositoin ->
            state.closestPageToPosition(anchorPositoin)?.prevKey?.minus(20)
                ?: state.closestPageToPosition(anchorPositoin)?.nextKey?.plus(20)
        }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonModel> {
        return try {
            val offset = params.key ?: 0
            val response = pokemonApi.getPokemon(
                offset = offset,
                limit = offset + 20,
            )
            val listPokemon = response.body()!!.results!!.map { it.toPokemonModel() }
            listPokemon.map {
                pokemonDao.insertPokemon(it)
            }

            LoadResult.Page(
                data = listPokemon,
                prevKey = if (offset == 0) null else offset - 20,
                nextKey = if (listPokemon.isEmpty()) null else offset.plus(20),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}