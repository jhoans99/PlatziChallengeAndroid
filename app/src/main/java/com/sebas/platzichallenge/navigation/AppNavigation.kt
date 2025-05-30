package com.sebas.platzichallenge.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sebas.platzichallenge.features.detail.ui.DetailRoute
import com.sebas.platzichallenge.features.search.ui.SearchMovieRoute

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController,"moviesScreen") {
        composable("moviesScreen") {
            SearchMovieRoute {
                navController.navigate("detailsMovie/${it}")
            }
        }

        composable(
            "detailsMovie/{id}",
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