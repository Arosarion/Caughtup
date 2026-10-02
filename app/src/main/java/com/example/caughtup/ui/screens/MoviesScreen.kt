package com.example.caughtup.ui.screens

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.caughtup.data.sampleWatchNext
import com.example.caughtup.data.sampleWatchedRecently

// Same shape as ShowsHomeScreen (shared via TrackedMediaScreen), just fewer
// sections — movies don't have a "Follow Up" equivalent, only Watch Next / Watched.
@Composable
fun MovieScreen(navController: NavController) {
    TrackedMediaScreen(
        sections = listOf(
            MediaSection(title = "Watch next", chipText = "This Week", shows = sampleWatchNext),
            MediaSection(title = "Watched Recently", shows = sampleWatchedRecently)
        ),
        emptyMessage = "You're not tracking any movies yet.",
        navController = navController
    )
}

@Preview(showBackground = true, widthDp = 402, heightDp = 874)
@Composable
private fun MovieScreenPreview() {
    MaterialTheme {
        MovieScreen(navController = rememberNavController())
    }
}