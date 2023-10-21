package com.fredericho.pokedex.presentation.home

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.fredericho.pokedex.R
import com.fredericho.pokedex.presentation.home.components.CardPokemon
import com.fredericho.pokedex.presentation.home.components.TopBar
import com.fredericho.pokedex.utils.isNetworkConnected

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigateToDetail: (String) -> Unit,
    context: Context = LocalContext.current,
) {
    val state by homeViewModel.state.collectAsState()
    val pokemonState by homeViewModel.pokemonState.collectAsState()
    val pagingPokemon by homeViewModel.pagingState.collectAsState()
    val pokemons = pagingPokemon.pokemons.collectAsLazyPagingItems()

    var isSearchActive by remember { mutableStateOf(false) }

    LaunchedEffect(state.field) {
        if (state.field.isEmpty()) {
            isSearchActive = false
        } else {
            isSearchActive = true
            homeViewModel.searchPokemon()
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                title = "Pokedex",
                icon = R.drawable.ic_pokemon,
                expanded = state.isExpanded,
                field = state.field,
                onValueChange = { newValue ->
                    homeViewModel.changeValueField(newValue)
                },
                onChangeExpanded = {
                    homeViewModel.changeExpanded(!state.isExpanded)
                },
                onDismissExpanded = {
                    homeViewModel.dismissExpanded()
                }
            )
        },
        modifier = Modifier.nestedScroll(TopAppBarDefaults.pinnedScrollBehavior().nestedScrollConnection)
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                if (isSearchActive) {
                    if (pagingPokemon.searchPokemon.isEmpty()) {
                        item {
                            Text(text = "Pokemon not found", style = MaterialTheme.typography.titleLarge)
                        }
                    } else {
                        items(
                            items = pagingPokemon.searchPokemon,
                            key = { it.name }
                        ) { pokemon ->
                            CardPokemon(
                                name = pokemon.name,
                                onDetailPokemon = {
                                    if (context.isNetworkConnected){
                                        pokemon.name
                                    } else {
                                        Toast.makeText(context, "Please connect to internet", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                })
                        }
                    }
                } else {
                    items(
                        count = pokemons.itemCount,
                        key = pokemons.itemKey { it.name }
                    ) { index ->
                        val pokemon = pokemons[index]
                        CardPokemon(
                            name = pokemon?.name.toString(),
                            onDetailPokemon = { navigateToDetail(pokemon?.name.toString()) })
                    }
                }
            }
        }

    }
}