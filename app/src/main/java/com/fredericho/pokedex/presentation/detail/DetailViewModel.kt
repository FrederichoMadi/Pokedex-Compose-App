package com.fredericho.pokedex.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fredericho.pokedex.data.pokemon.implementation.remote.response.DetailResponse
import com.fredericho.pokedex.data.pokemon.repository.PokemonRepository
import com.fredericho.pokedex.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _detailState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val detailState = _detailState.asStateFlow()

    fun getDetail(name: String) {
        viewModelScope.launch {
            repository.getPokemonByName(name).collect {
                when(it) {
                    is Resource.Loading -> {
                        _detailState.value = DetailUiState.Loading
                    }
                    is Resource.Error -> {
                        _detailState.value = DetailUiState.Error(
                            message = it.message
                        )
                    }
                    is Resource.Success -> {
                        _detailState.value = DetailUiState.Success(
                            detail = it.data
                        )
                    }
                }
            }

        }
    }
}

sealed interface DetailUiState{
    object Loading : DetailUiState
    data class Error(val message: String) : DetailUiState
    data class Success(val detail: DetailResponse) : DetailUiState
}