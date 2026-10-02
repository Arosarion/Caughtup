package com.example.caughtup.data

import com.example.caughtup.R
import com.example.caughtup.ui.components.ShowPreview

// Centralized sample data so both screens AND the show-detail lookup
// (findShowByTitle, below) read from the same source. Delete this whole file
// once Room/TMDB are wired up — this only exists to make the UI demoable now.

private const val TMDB_IMAGE_BASE = "https://image.tmdb.org/t/p/w342"

val sampleNextUp = listOf(
    ShowPreview(
        title = "Lantern",
        topTag = "S1E4 · Episode Premiere",
        caption = "The Weenie starts in 2h 15m",
        posterRes = R.drawable.lanterns_poster
    )
)

val sampleFollowUp = listOf(
    ShowPreview(
        title = "Severance",
        topTag = "S2E1 · Episode Premiere",
        caption = "\"The We We Are\" starts in 2d",
        posterUrl = "$TMDB_IMAGE_BASE/7acCr70gsRhFCzs1F5cA9quOqjq.jpg"
    )
)

val sampleLastSeen = listOf(
    ShowPreview(
        title = "The Penguin",
        topTag = "S1E8",
        caption = "Last seen 3 days ago",
        posterUrl = "$TMDB_IMAGE_BASE/dGRe1P4ejFbb2N7HrbGCUKM6oO0.jpg"
    )
)

// TODO: replace with real TMDB posterPath values once the API call is wired up.
// Left as null for now rather than guessing — AsyncImage just shows the dark
// placeholder background until a real URL is set.
val sampleWatchNext = listOf(
    ShowPreview(
        title = "Breakfast at Tiffany's",
        topTag = "1961 · 1h 55m",
        caption = "A young New York socialite becomes interested in..."
    ),
    ShowPreview(
        title = "Children of Men",
        topTag = "2006 · 1h 49m",
        caption = "In 2027, in a chaotic world in which women have..."
    )
)

val sampleWatchedRecently = listOf(
    ShowPreview(
        title = "Obsession",
        topTag = "2026 · 1h 49m",
        caption = "A tense thriller that keeps you guessing until the end."
    )
)

private val allSampleShows: List<ShowPreview> =
    sampleNextUp + sampleFollowUp + sampleLastSeen + sampleWatchNext + sampleWatchedRecently

// Used by the show-detail route to turn the title that came back through
// NavHost's arguments into the actual ShowPreview to display.
// Returns null if title is null or nothing matches (e.g. a stale/bad link) —
// ShowDetailScreen is responsible for handling that null case gracefully.
fun findShowByTitle(title: String?): ShowPreview? =
    allSampleShows.firstOrNull { it.title == title }