package com.fredericho.pokedex.data.pokemon.implementation.remote.response

import com.google.gson.annotations.SerializedName

data class PokemonResponse(

	@field:SerializedName("next")
	val next: String? = null,

	@field:SerializedName("previous")
	val previous: Any? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("results")
	val results: List<PokemonItemResponse>? = null
)

data class PokemonItemResponse(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)
