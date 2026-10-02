package com.example.caughtup.ui.screens

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.caughtup.data.sampleFollowUp
import com.example.caughtup.data.sampleLastSeen
import com.example.caughtup.data.sampleNextUp

// Just the screen's content now — no Scaffold/TopBar/BottomBar here.
// CaughtupApp.kt owns those and are shared across every screen.
// The actual layout work happens in TrackedMediaScreen, shared with MovieScreen.
// navController is only threaded through so a tapped card can navigate to
// its detail screen — see TrackedMediaScreen for where that happens.
@Composable
fun ShowsHomeScreen(navController: NavController) {
    TrackedMediaScreen(
        sections = listOf(
            MediaSection(title = "Next Up", chipText = "Tonight", shows = sampleNextUp),
            MediaSection(title = "Follow Up", chipText = "Tomorrow", shows = sampleFollowUp),
            MediaSection(title = "Last Seen", shows = sampleLastSeen)
        ),
        emptyMessage = "You're not tracking any shows yet.",
        navController = navController
    )
}

@Preview(showBackground = true, widthDp = 402, heightDp = 874)
@Composable
private fun ShowsHomeScreenPreview() {
    MaterialTheme {
        ShowsHomeScreen(navController = rememberNavController())
    }
}