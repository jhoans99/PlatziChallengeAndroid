package com.sebas.platzichallenge.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sebas.platzichallenge.features.detail.ui.DetailRoute
import com.sebas.platzichallenge.features.search.ui.SearchMovieRoute
import com.sebas.platzichallenge.navigation.Routes.DetailMovieRoute
import com.sebas.platzichallenge.navigation.Routes.SearchRoute

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController,SearchRoute.route) {
        composable(SearchRoute.route) {
            SearchMovieRoute {
                navController.navigate(DetailMovieRoute.createRoute(it))
            }
        }

        composable(
            DetailMovieRoute.route,
            arguments = listOf(
                navArgument("id") { type = NavType.StringType }
            )
        ) {
            DetailRoute {
                navController.popBackStack()
            }
        }
    }
}