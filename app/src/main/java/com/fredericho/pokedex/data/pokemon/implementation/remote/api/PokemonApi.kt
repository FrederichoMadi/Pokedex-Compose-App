package com.fredericho.pokedex.data.pokemon.implementation.remote.api

import com.fredericho.pokedex.data.pokemon.implementation.remote.response.DetailResponse
import com.fredericho.pokedex.data.pokemon.implementation.remote.response.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon")
    suspend fun getPokemon(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): Response<PokemonResponse>

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(
        @Path("name") name: String,
    ): Response<DetailResponse>
}