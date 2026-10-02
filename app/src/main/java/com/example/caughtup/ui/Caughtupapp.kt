package com.example.caughtup.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.caughtup.ui.components.BottomNavBar
import com.example.caughtup.data.findEpisodeDetailByTitle
import com.example.caughtup.data.findMovieDetailByTitle
import com.example.caughtup.data.findShowDetailByTitle
import com.example.caughtup.ui.navigation.EPISODE_DETAIL_ROUTE
import com.example.caughtup.ui.navigation.MOVIE_DETAIL_ROUTE
import com.example.caughtup.ui.navigation.SHOW_DETAIL_ROUTE
import com.example.caughtup.ui.navigation.Screen
import com.example.caughtup.ui.screens.EpisodeDetailScreen
import com.example.caughtup.ui.screens.ExploreScreen
import com.example.caughtup.ui.screens.MovieDetailScreen
import com.example.caughtup.ui.screens.MovieScreen
import com.example.caughtup.ui.screens.ProfileScreen
import com.example.caughtup.ui.screens.ShowDetailScreen
import com.example.caughtup.ui.screens.ShowsHomeScreen
import com.example.caughtup.ui.screens.SplashScreen
import com.example.caughtup.ui.theme.CaughtupBackground
import com.example.caughtup.ui.theme.PrimaryWordColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaughtupApp() {
    val navController = rememberNavController()

    // The splash screen owns the whole display briefly — no top bar, no bottom
    // nav bar while it's showing. Everything else keeps both, same as before.
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val showChrome = currentRoute != Screen.Splash.route

    Scaffold(
        topBar = {
            if (showChrome) {
                TopAppBar(
                    title = { Text("Caughtup") },
                    actions = {
                        IconButton(onClick = { /* TODO: release-date calendar, no-op for now */ }) {
                            Icon(imageVector = Icons.Filled.DateRange, contentDescription = "Calendar")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = CaughtupBackground,
                        titleContentColor = PrimaryWordColor
                    )
                )
            }
        },
        bottomBar = { if (showChrome) BottomNavBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = if (showChrome) Modifier.padding(innerPadding) else Modifier
        ) {
            composable(Screen.Splash.route) { SplashScreen(navController = navController) }
            composable(Screen.Shows.route) { ShowsHomeScreen(navController = navController) }
            composable(Screen.Movies.route) { MovieScreen(navController = navController) }
            composable(Screen.Explore.route) { ExploreScreen() }
            composable(Screen.Profile.route) { ProfileScreen() }
            composable(
                route = SHOW_DETAIL_ROUTE,
                arguments = listOf(navArgument("title") { type = NavType.StringType })
            ) { backStackEntry ->
                val title = backStackEntry.arguments?.getString("title")
                ShowDetailScreen(show = findShowDetailByTitle(title), navController = navController)
            }
            composable(
                route = EPISODE_DETAIL_ROUTE,
                arguments = listOf(navArgument("title") { type = NavType.StringType })
            ) { backStackEntry ->
                val title = backStackEntry.arguments?.getString("title")
                EpisodeDetailScreen(episode = findEpisodeDetailByTitle(title), navController = navController)
            }
            composable(
                route = MOVIE_DETAIL_ROUTE,
                arguments = listOf(navArgument("title") { type = NavType.StringType })
            ) { backStackEntry ->
                val title = backStackEntry.arguments?.getString("title")
                MovieDetailScreen(movie = findMovieDetailByTitle(title), navController = navController)
            }
        }
    }
}