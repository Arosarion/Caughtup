package com.example.caughtup.ui.components

// The whole-movie detail page — hero, rating/runtime/year, genre tags, watchlist/
// watched toggles, synopsis, cast, and a "rate the movie" row. No seasons/episodes —
// that's what distinguishes this from ShowDetail. Reuses CastMember from EpisodeDetail.kt
// rather than redefining it.
data class MovieDetail(
    val title: String,
    val rating: Double,
    val runtimeMinutes: Int,
    val year: Int,
    val genres: List<String>,
    val synopsis: String,
    val cast: List<CastMember> = emptyList(),
    val heroUrl: String? = null,
    val heroRes: Int? = null,
    val isInWatchlist: Boolean = false,
    val isWatched: Boolean = false
)