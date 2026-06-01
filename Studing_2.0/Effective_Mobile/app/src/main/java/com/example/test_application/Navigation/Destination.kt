package com.example.test_application.Navigation

sealed class Destination(val route: String) {

    data object LOGIN : Destination(ROUTE_LOGIN)
    data object MAIN : Destination(ROUTE_MAIN)
    data object FAVORITES : Destination(ROUTE_FAVORITES)
    data object ACCOUNT : Destination(ROUTE_ACCOUNT)

    companion object {
        private const val ROUTE_LOGIN = "route_login"
        private const val ROUTE_MAIN = "route_main"
        private const val ROUTE_FAVORITES = "route_favorites"
        private const val ROUTE_ACCOUNT = "route_account"
    }
}