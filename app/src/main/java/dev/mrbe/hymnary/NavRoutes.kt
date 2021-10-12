package dev.mrbe.hymnary

sealed class NavRoutes(val route: String) {
    object Hymn : NavRoutes("hymn")
    object Details : NavRoutes("details")
}