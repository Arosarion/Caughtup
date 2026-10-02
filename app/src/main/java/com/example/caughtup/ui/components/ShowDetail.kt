package com.example.caughtup.ui.components

// One row in a show's Episodes list — distinct from EpisodeDetail (the full episode
// page's data) since a list row only needs a summary, not the full synopsis/cast.
data class EpisodeRow(
    // Unique per episode — passed to episodeDetailRoute() so tapping this row opens
    // the RIGHT episode, not just "whatever episode this show's card happens to show".
    val routeKey: String,
    val episodeNumber: Int,
    val episodeTitle: String,
    val airDate: String,
    val rating: Double,
    val isNextUp: Boolean = false,
    val isWatched: Boolean = false,
    val thumbnailUrl: String? = null,
    val thumbnailRes: Int? = null
)

// The TV-show overview page (hero, season/genre info, synopsis, episode list) —
// distinct from a single EpisodeDetail, and distinct from a movie's detail page
// (movies have no seasons/episodes — that's its own screen, built next session).
data class ShowDetail(
    val title: String,
    val rating: Double,
    val seasonCount: Int,
    val episodeCount: Int,
    val network: String,
    val genres: List<String>,
    val currentSeasonLabel: String,
    val synopsis: String,
    val episodes: List<EpisodeRow>,
    val heroUrl: String? = null,
    val heroRes: Int? = null,
    val isInWatchlist: Boolean = false,
    val isWatched: Boolean = false
)