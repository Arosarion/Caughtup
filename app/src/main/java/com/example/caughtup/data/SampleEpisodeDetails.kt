package com.example.caughtup.data

import com.example.caughtup.ui.components.CastMember
import com.example.caughtup.ui.components.EpisodeDetail

// Keyed to match "Lantern" from SampleShows.kt's sampleNextUp (so the nav route that
// already works still resolves) even though this episode's own data writes the show
// name as "Lanterns" — pick one spelling and make these consistent later.
private val lanternsEpisode3 = EpisodeDetail(
    routeKey = "Lanterns-E3",
    showTitle = "Lanterns",
    seasonNumber = 1,
    episodeNumber = 3,
    episodeTitle = "OutKast",
    runtimeMinutes = 49,
    airDate = "Aug 30, 2026",
    synopsis = "A tense standoff forces John and Hal to reconsider everything they thought they knew.",
    cast = listOf(
        CastMember(name = "Kyle Chandler"),
        CastMember(name = "Aaron Pierre")
    ),
    commentCount = 84
)

private val lanternsEpisode4 = EpisodeDetail(
    routeKey = "Lanterns-E4",
    showTitle = "Lanterns",
    seasonNumber = 1,
    episodeNumber = 4,
    episodeTitle = "The Weenie",
    runtimeMinutes = 52,
    airDate = "Sep 6, 2026",
    synopsis = "As the 48-hour deadline approaches, John develops a new plan that could change everything.",
    cast = listOf(
        CastMember(name = "Kyle Chandler"),
        CastMember(name = "Aaron Pierre"),
        CastMember(name = "Kelly Macdonald"),
        CastMember(name = "Poorna Jagannathan")
    ),
    commentCount = 122
)

private val allSampleEpisodes = listOf(lanternsEpisode3, lanternsEpisode4)

// Tries an exact routeKey match first (used by the Episodes list on ShowDetailScreen,
// e.g. "Lanterns-E3") and falls back to matching by show title (used by ShowCard taps,
// which only know the show's title, e.g. "Lantern") — returns the first episode for
// that show in that case, same behavior as before this file supported multiple episodes.
fun findEpisodeDetailByTitle(key: String?): EpisodeDetail? {
    if (key == null) return null
    return allSampleEpisodes.firstOrNull { it.routeKey.equals(key, ignoreCase = true) }
        ?: allSampleEpisodes.firstOrNull { it.showTitle.startsWith(key, ignoreCase = true) }
}