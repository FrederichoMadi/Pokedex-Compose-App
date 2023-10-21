package com.fredericho.pokedex.data.pokemon.implementation.mapping

import com.fredericho.pokedex.data.pokemon.implementation.database.model.PokemonModel
import com.fredericho.pokedex.data.pokemon.implementation.remote.response.PokemonItemResponse

internal fun PokemonItemResponse.toPokemonModel() : PokemonModel =
    PokemonModel(name.toString(), url.toString())