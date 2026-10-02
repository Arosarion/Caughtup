package com.example.caughtup.ui.components

// A cast member shown in the horizontal scroller on the episode page.
// photoUrl/photoRes both null for now (no real headshot assets/API yet) —
// EpisodeDetailScreen falls back to a plain initial-letter circle when both are null.
data class CastMember(
    val name: String,
    val photoUrl: String? = null,
    val photoRes: Int? = null
)

// Everything the episode-detail page needs. Deliberately separate from ShowPreview
// (the card model) rather than cramming more fields into it — a card only ever
// shows a title/tag/caption, this page shows a lot more (cast, rating, comments).
// TODO: once TMDB/Room exist, this becomes real data instead of hand-written samples,
// and showTitle should probably become a real show ID instead of matching by name.
data class EpisodeDetail(
    // Unique per episode (e.g. "Lanterns-E4") — matched against whatever string
    // gets passed to episodeDetailRoute(). A plain show title like "Lantern" also
    // still matches via the showTitle fallback in findEpisodeDetailByTitle, so
    // existing ShowCard taps (which only know the show's title) keep working.
    val routeKey: String,
    val showTitle: String,
    val seasonNumber: Int,
    val episodeNumber: Int,
    val episodeTitle: String,
    val runtimeMinutes: Int,
    val airDate: String,
    val synopsis: String,
    val stillUrl: String? = null,
    val stillRes: Int? = null,
    val cast: List<CastMember> = emptyList(),
    // TODO: real comment data/screen later — this is just the count shown on the row.
    val commentCount: Int = 0
)