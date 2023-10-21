import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fredericho.pokedex.navigation.NavDestination
import com.fredericho.pokedex.presentation.detail.DetailScreen
import com.fredericho.pokedex.presentation.home.HomeScreen

@Composable
fun PokedexNavigation(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = NavDestination.HOME.route,
    ) {
        composable(NavDestination.HOME.route){
            HomeScreen(
                navigateToDetail = { name ->
                    navController.navigate("${NavDestination.DETAIL_POKEMON.route}/$name")
                }
            )
        }
        composable("${NavDestination.DETAIL_POKEMON.route}/{name}",
            arguments = listOf(navArgument("name") {type = NavType.StringType})
        ){
            val name = it.arguments?.getString("name") ?: ""

            DetailScreen(name = name, onNavigationBack = { navController.popBackStack()})
        }
    }
}