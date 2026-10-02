package com.example.caughtup.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.caughtup.ui.components.ShowCard
import com.example.caughtup.ui.components.ShowPreview
import com.example.caughtup.ui.navigation.episodeDetailRoute
import com.example.caughtup.ui.navigation.movieDetailRoute
import com.example.caughtup.ui.navigation.showDetailRoute
import com.example.caughtup.ui.theme.HighlightedColor
import com.example.caughtup.ui.theme.PrimaryWordColor
import com.example.caughtup.ui.theme.SecWordColor

// SHOW: tapping a card opens that specific EPISODE (the card shows "S1E4 · ...").
// MOVIE: tapping a card opens that movie's own detail page — a movie has no
// episodes, so its "whole item" page and its "detail" page are the same thing.
enum class MediaKind { SHOW, MOVIE }

// One group of cards under a header — "Next Up", "Watch next", etc.
// chipText is the small badge next to the header ("Tonight", "This Week"); null = no chip.
data class MediaSection(
    val title: String,
    val chipText: String? = null,
    val shows: List<ShowPreview>,
    val kind: MediaKind = MediaKind.SHOW
)

// Shared by ShowsHomeScreen and MovieScreen — both are really the same screen shape
// ("a list of tracked media grouped into sections"), just with different section
// names and different card text. Add/remove functionality and the real empty-state
// trigger (no shows tracked at all) will hook in here once there's a ViewModel
// backing the lists instead of hardcoded sample data.
@Composable
fun TrackedMediaScreen(
    sections: List<MediaSection>,
    emptyMessage: String,
    navController: NavController
) {
    val hasAnyShows = sections.any { it.shows.isNotEmpty() }

    if (!hasAnyShows) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = emptyMessage,
                color = SecWordColor,
                fontSize = 16.sp
            )
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        sections.forEach { section ->
            // Skip sections with nothing in them instead of showing an empty header.
            if (section.shows.isEmpty()) return@forEach

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = section.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryWordColor
                    )
                    if (section.chipText != null) {
                        Text(
                            text = section.chipText,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = HighlightedColor,
                            modifier = Modifier
                                .background(
                                    HighlightedColor.copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }
            items(section.shows) { show ->
                ShowCard(
                    show = show,
                    onClick = {

                        val route = when (section.kind) {
                            MediaKind.SHOW -> episodeDetailRoute(show.title)
                            MediaKind.MOVIE -> movieDetailRoute(show.title)
                        }
                        navController.navigate(route)
                    }
                )
            }
        }
    }
}