package com.fredericho.pokedex.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.fredericho.pokedex.data.pokemon.implementation.database.model.PokemonModel
import com.fredericho.pokedex.data.pokemon.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val homeState = MutableStateFlow(HomeState())
    val state = homeState.asStateFlow()

    private val _pokemonState = MutableStateFlow<PokemonState>(PokemonState.Loading)
    val pokemonState = _pokemonState.asStateFlow()

    private val _pagingState = MutableStateFlow(ListPokemon())
    val pagingState = _pagingState.asStateFlow()

    init {
        getPokemon()
        getPokemonLocal()
    }

    private fun getPokemon() {
        viewModelScope.launch {
            val pokemons = pokemonRepository.getPokemon().cachedIn(viewModelScope)
            _pagingState.update {
                it.copy(pokemons = pokemons)
            }
        }
    }

    private fun getPokemonLocal() {
        viewModelScope.launch {
            _pagingState.update {
                it.copy(
                    pokemonLocal = pokemonRepository.getPokemonOffline()
                )
            }
        }
    }

    fun searchPokemon() {
        viewModelScope.launch {
            val searchPokemon =
                _pagingState.value.pokemonLocal.filter { it.name.contains(homeState.value.field) }
            _pagingState.update {
                it.copy(
                    searchPokemon = searchPokemon
                )
            }
        }
    }

    fun changeValueField(newValue: String) {
        homeState.update { data ->
            data.copy(
                field = newValue
            )
        }
    }

    fun changeExpanded(newValue: Boolean) {
        homeState.update { data ->
            data.copy(
                isExpanded = newValue
            )
        }
    }

    fun dismissExpanded() {
        homeState.update {
            it.copy(isExpanded = false)
        }
    }

}

data class HomeState(
    val field: String = "",
    val isExpanded: Boolean = false,
)

data class ListPokemon(
    val pokemons: Flow<PagingData<PokemonModel>> = emptyFlow(),
    val pokemonLocal: List<PokemonModel> = emptyList(),
    val searchPokemon: List<PokemonModel> = emptyList(),
)

sealed interface PokemonState {
    object Loading : PokemonState
    data class Error(val message: String) : PokemonState
    data class Success(val pokemons: Flow<PagingData<PokemonModel>>) : PokemonState
}