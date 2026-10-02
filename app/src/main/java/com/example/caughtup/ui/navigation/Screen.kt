package com.example.caughtup.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.LocalMovies
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

// Single source of truth for every bottom-nav destination:
// route = used by NavHost for navigation, label/icon = used by BottomNavBar for display.
sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Splash : Screen("splash", "Splash", Icons.Filled.Tv)
    // icon unused, never shown in nav bar
    object Shows : Screen("shows", "Shows", Icons.Filled.Tv)
    object Movies : Screen("movies", "Movies", Icons.Filled.LocalMovies)
    object Explore : Screen("explore", "Explore", Icons.Filled.Search)
    object Profile : Screen("profile", "Profile", Icons.Filled.Person)

    companion object {
        val bottomNavItems = listOf(Shows, Movies, Explore, Profile)
    }
}

// Not a bottom-nav tab — only reachable by tapping a ShowCard, so it lives outside
// the sealed class above (it has no label/icon for the nav bar).
// Used for the Movies tab — a movie has no episodes, so "the whole item's detail
// page" is the only detail page it needs.
const val SHOW_DETAIL_ROUTE = "show_detail/{title}"

fun showDetailRoute(title: String) = "show_detail/$title"

// Shows (not movies) route here instead — tapping a show card should land on the
// specific episode it's advertising ("S1E4 · Episode Premiere"), not a show overview.
const val EPISODE_DETAIL_ROUTE = "episode_detail/{title}"

fun episodeDetailRoute(title: String) = "episode_detail/$title"

const val MOVIE_DETAIL_ROUTE = "movie_detail/{title}"

fun movieDetailRoute(title: String) = "movie_detail/$title"