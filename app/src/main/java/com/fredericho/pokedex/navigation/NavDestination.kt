package com.fredericho.pokedex.navigation

enum class NavDestination(
    val route: String,
) {
    HOME(
      route = "home",
    ),
    DETAIL_POKEMON(
        route = "detail_pokemon",
    )
}