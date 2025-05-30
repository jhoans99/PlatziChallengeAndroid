package com.sebas.platzichallenge.navigation

sealed class Routes(val route: String) {
    object SearchRoute: Routes("searchMovie")

    object DetailMovieRoute: Routes("detailMovie/{id}") {
        fun createRoute(id: String) = "detailMovie/${id}"
    }

}