package com.example.caughtup.data

import com.example.caughtup.ui.components.EpisodeRow
import com.example.caughtup.ui.components.ShowDetail

private val lanternsShowDetail = ShowDetail(
    title = "Lanterns",
    rating = 4.5,
    seasonCount = 1,
    episodeCount = 9,
    network = "Apple TV+",
    genres = listOf("Action", "Superhero", "Drama", "Mystery"),
    currentSeasonLabel = "Season 1",
    synopsis = "Lanterns follows new recruit John Stewart and Lantern legend Hal " +
            "Jordan, two intergalactic cops drawn into a dark, Earth-based mystery as " +
            "they investigate a murder in the American heartland.",
    episodes = listOf(
        EpisodeRow(
            routeKey = "Lanterns-E3",
            episodeNumber = 3,
            episodeTitle = "OutKast",
            airDate = "Aug 30, 2026",
            rating = 4.3,
            isWatched = true
        ),
        EpisodeRow(
            routeKey = "Lanterns-E4",
            episodeNumber = 4,
            episodeTitle = "The Weenie",
            airDate = "Sep 6, 2026",
            rating = 4.5,
            isNextUp = true
        )
    ),
    isInWatchlist = true
)

private val allSampleShowDetails = listOf(lanternsShowDetail)

fun findShowDetailByTitle(title: String?): ShowDetail? {
    if (title == null) return null
    return allSampleShowDetails.firstOrNull { it.title.startsWith(title, ignoreCase = true) }
}