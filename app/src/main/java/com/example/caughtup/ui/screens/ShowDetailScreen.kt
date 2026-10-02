package com.example.caughtup.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.caughtup.ui.components.EpisodeRow
import com.example.caughtup.ui.components.ShowDetail
import com.example.caughtup.ui.navigation.episodeDetailRoute
import com.example.caughtup.ui.theme.CaughtupTheme
import com.example.caughtup.ui.theme.HighlightedColor
import com.example.caughtup.ui.theme.PrimaryWordColor
import com.example.caughtup.ui.theme.SecWordColor

// The TV-show OVERVIEW page — season/genre info, watchlist/watched toggles, synopsis,
// and the full episode list (each row routes into EpisodeDetailScreen for that episode).
// Distinct from EpisodeDetailScreen (one episode) and the future movie-detail screen
// (movies have no seasons/episodes, so they need their own layout, not this one).
@Composable
fun ShowDetailScreen(show: ShowDetail?, navController: NavController) {
    if (show == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Couldn't find that show.", color = SecWordColor, fontSize = 16.sp)
        }
        return
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            // Hero image with back/bookmark icons in circular backdrops, matching Figma.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) {
                if (show.heroRes != null) {
                    Image(
                        painter = painterResource(id = show.heroRes),
                        contentDescription = show.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    AsyncImage(
                        model = show.heroUrl,
                        contentDescription = show.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CircleIconButton(
                        icon = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        onClick = { navController.popBackStack() }
                    )
                    // TODO: wire up real bookmarking once there's a data layer — no-op for now.
                    CircleIconButton(
                        icon = if (show.isInWatchlist) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                        contentDescription = "Save show",
                        onClick = { },
                        tint = if (show.isInWatchlist) HighlightedColor else Color.White
                    )
                }
            }
        }

        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = show.title,
                    color = PrimaryWordColor,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    val seasonWord = if (show.seasonCount == 1) "Season" else "Seasons"
                    Text(
                        text = "${show.rating} · ${show.seasonCount} $seasonWord (${show.episodeCount} Ep) · ${show.network}",
                        color = SecWordColor,
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    show.genres.forEach { genre ->
                        GenreChip(genre)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    // TODO: both buttons are display-only until there's a data layer.
                    ActionButton(
                        label = "In Watchlist",
                        icon = if (show.isInWatchlist) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                        active = show.isInWatchlist,
                        modifier = Modifier.weight(1f)
                    )
                    ActionButton(
                        label = "Mark Watched",
                        icon = if (show.isWatched) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        active = show.isWatched,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))
                // TODO: make this a real dropdown once there's more than one season of sample data.
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, SecWordColor.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                        .clickable { }
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(text = show.currentSeasonLabel, color = PrimaryWordColor, fontSize = 13.sp)
                }

                Spacer(modifier = Modifier.height(18.dp))
                Text(text = "Synopsis", color = PrimaryWordColor, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = show.synopsis, color = SecWordColor, fontSize = 14.sp)

                Spacer(modifier = Modifier.height(18.dp))
                Text(text = "Episodes", color = PrimaryWordColor, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }

        items(show.episodes) { episode ->
            EpisodeRowCard(
                episode = episode,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                onClick = { navController.navigate(episodeDetailRoute(episode.routeKey)) }
            )
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

@Composable
private fun CircleIconButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    tint: Color = Color.White
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(Color.Black.copy(alpha = 0.4f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = onClick) {
            Icon(imageVector = icon, contentDescription = contentDescription, tint = tint)
        }
    }
}

@Composable
private fun GenreChip(label: String) {
    Text(
        text = label,
        color = SecWordColor,
        fontSize = 12.sp,
        modifier = Modifier
            .border(1.dp, SecWordColor.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    )
}

@Composable
private fun ActionButton(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    active: Boolean,
    modifier: Modifier = Modifier
) {
    val color = if (active) HighlightedColor else SecWordColor
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, color.copy(alpha = if (active) 1f else 0.4f), RoundedCornerShape(10.dp))
            .clickable { }
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = label, color = color, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun EpisodeRowCard(episode: EpisodeRow, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val highlight = episode.isNextUp
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF1B1B2F))
            .border(
                width = if (highlight) 1.dp else 0.dp,
                color = if (highlight) HighlightedColor else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(width = 72.dp, height = 56.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF2A2A3D))
        ) {
            if (episode.thumbnailRes != null) {
                Image(
                    painter = painterResource(id = episode.thumbnailRes),
                    contentDescription = episode.episodeTitle,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else if (episode.thumbnailUrl != null) {
                AsyncImage(
                    model = episode.thumbnailUrl,
                    contentDescription = episode.episodeTitle,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "E${episode.episodeNumber} · ${episode.episodeTitle}",
                color = PrimaryWordColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = if (highlight) "Next Up · ${episode.airDate}" else episode.airDate,
                color = if (highlight) HighlightedColor else SecWordColor,
                fontSize = 12.sp
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = Color(0xFFFFC107),
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(text = episode.rating.toString(), color = SecWordColor, fontSize = 12.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .border(
                        1.dp,
                        if (episode.isWatched || highlight) HighlightedColor else SecWordColor.copy(alpha = 0.4f),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = if (episode.isWatched) "Watched" else "Mark as watched",
                    tint = if (episode.isWatched || highlight) HighlightedColor else SecWordColor,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 402, heightDp = 1000)
@Composable
private fun ShowDetailScreenPreview() {
    CaughtupTheme {
        ShowDetailScreen(
            show = ShowDetail(
                title = "Lanterns",
                rating = 4.5,
                seasonCount = 1,
                episodeCount = 9,
                network = "Apple TV+",
                genres = listOf("Action", "Superhero", "Drama", "Mystery"),
                currentSeasonLabel = "Season 1",
                synopsis = "Lanterns follows new recruit John Stewart and Lantern legend Hal Jordan.",
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
            ),
            navController = rememberNavController()
        )
    }
}