package com.example.caughtup.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.caughtup.ui.navigation.Screen
import com.example.caughtup.ui.theme.CaughtupBackground
import com.example.caughtup.ui.theme.HighlightedColor
import com.example.caughtup.ui.theme.SecWordColor

// One shared nav bar, used by every screen via CaughtupApp's Scaffold.
// currentRoute is read from the NavController so the right tab highlights automatically.
//
// Double-tap rule: tapping the SAME tab icon twice within DOUBLE_TAP_WINDOW_MS is treated
// as "take me all the way back to that tab's home screen" — it clears anything pushed on
// top within that tab (e.g. Episode Detail, Show Detail, Movie Detail) via a hard
// popUpTo(...){ inclusive = true }. A single tap keeps the old saveState/restoreState
// behavior so ordinary tab-switching still feels seamless.
private const val DOUBLE_TAP_WINDOW_MS = 400L

@Composable
fun BottomNavBar(navController: NavController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    var lastTappedRoute by remember { mutableStateOf<String?>(null) }
    var lastTapTime by remember { mutableLongStateOf(0L) }

    NavigationBar(
        containerColor = CaughtupBackground
    ) {
        Screen.bottomNavItems.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    val now = System.currentTimeMillis()
                    val isDoubleTap = lastTappedRoute == screen.route &&
                            (now - lastTapTime) < DOUBLE_TAP_WINDOW_MS

                    // Reset so a third rapid tap doesn't keep re-triggering double-tap logic
                    // forever (it has to be re-earned by a fresh pair of taps).
                    lastTappedRoute = screen.route
                    lastTapTime = now

                    if (isDoubleTap) {
                        navController.navigate(screen.route) {
                            popUpTo(screen.route) { inclusive = true }
                            launchSingleTop = true
                        }
                        lastTapTime = 0L
                    } else {
                        navController.navigate(screen.route) {
                            // Avoid piling up a huge back stack when switching tabs repeatedly.
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = { Icon(screen.icon, contentDescription = screen.label) },
                label = { Text(screen.label) },
                colors = NavigationBarItemDefaults.colors(
                    // Colors when this tab IS selected (the "tapped/highlighted" state)
                    selectedIconColor = HighlightedColor,
                    selectedTextColor = HighlightedColor,
                    indicatorColor = HighlightedColor.copy(alpha = 0.0f),
                    // Colors when this tab is NOT selected
                    unselectedIconColor = SecWordColor,
                    unselectedTextColor = SecWordColor
                )
            )
        }
    }
}