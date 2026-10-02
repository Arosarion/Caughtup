package com.example.caughtup.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.caughtup.ui.components.CastMember
import com.example.caughtup.ui.components.EpisodeDetail
import com.example.caughtup.ui.theme.CaughtupTheme
import com.example.caughtup.ui.theme.HighlightedColor
import com.example.caughtup.ui.theme.PrimaryWordColor
import com.example.caughtup.ui.theme.SecWordColor

private val RATING_LABELS = listOf("Bad", "OK", "Good", "Great", "Perfect")

// Reached by tapping a show card on the Shows tab (MediaKind.SHOW in TrackedMediaScreen).
// Uses EpisodeDetail (not ShowPreview) since this page needs a lot more than a card does —
// cast, rating options, runtime/air date, a comment count.
@Composable
fun EpisodeDetailScreen(episode: EpisodeDetail?, navController: NavController) {
    if (episode == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Couldn't find that episode.", color = SecWordColor, fontSize = 16.sp)
        }
        return
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            // Hero still image with back/bookmark icons floating over it.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            ) {
                if (episode.stillRes != null) {
                    Image(
                        painter = painterResource(id = episode.stillRes),
                        contentDescription = episode.episodeTitle,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    AsyncImage(
                        model = episode.stillUrl,
                        contentDescription = episode.episodeTitle,
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
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                    // TODO: wire up real bookmarking once there's a data layer — no-op for now.
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.BookmarkBorder, contentDescription = "Save episode", tint = Color.White)
                    }
                }
            }
        }

        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = episode.showTitle,
                    color = PrimaryWordColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Episode ${episode.episodeNumber} · ${episode.episodeTitle}",
                    color = PrimaryWordColor,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "Rate the Episode", color = SecWordColor, fontSize = 13.sp)
                Spacer(modifier = Modifier.height(8.dp))

                // TODO: track which pill (if any) is selected and persist it once
                // there's a data layer — these don't do anything yet.
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    RATING_LABELS.forEach { label ->
                        RatingPill(label = label, modifier = Modifier.weight(1f))
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Episode info",
                    color = PrimaryWordColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${episode.runtimeMinutes} min · ${episode.airDate}",
                    color = SecWordColor,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = episode.synopsis,
                    color = PrimaryWordColor,
                    fontSize = 14.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Cast",
                    color = PrimaryWordColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        item {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp)
            ) {
                items(episode.cast) { member ->
                    CastMemberItem(member)
                }
            }
        }

        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Spacer(modifier = Modifier.height(20.dp))
                // TODO: navigate to a real comments screen once one exists — no-op for now.
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF1B1B2F))
                        .clickable { }
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Comments", color = PrimaryWordColor, fontSize = 15.sp)
                    Text(
                        text = episode.commentCount.toString(),
                        color = HighlightedColor,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun RatingPill(label: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .border(1.dp, SecWordColor.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = null,
            tint = Color(0xFFFFC107),
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, color = PrimaryWordColor, fontSize = 11.sp)
    }
}

@Composable
private fun CastMemberItem(member: CastMember) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (member.photoRes != null || member.photoUrl != null) {
            if (member.photoRes != null) {
                Image(
                    painter = painterResource(id = member.photoRes),
                    contentDescription = member.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(56.dp).clip(CircleShape)
                )
            } else {
                AsyncImage(
                    model = member.photoUrl,
                    contentDescription = member.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(56.dp).clip(CircleShape)
                )
            }
        } else {
            // No real headshot asset/API yet — plain initial-letter placeholder.
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(Color(0xFF3A3A4D), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = member.name.first().toString(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = member.name,
            color = PrimaryWordColor,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.width(64.dp)
        )
    }
}

@Preview(showBackground = true, widthDp = 402, heightDp = 874)
@Composable
private fun EpisodeDetailScreenPreview() {
    CaughtupTheme {
        EpisodeDetailScreen(
            episode = EpisodeDetail(
                routeKey = "Lanterns-E4",
                showTitle = "Lanterns",
                seasonNumber = 1,
                episodeNumber = 4,
                episodeTitle = "The Weenie",
                runtimeMinutes = 52,
                airDate = "Sep 6, 2026",
                synopsis = "As the 48-hour deadline approaches, John develops a new plan.",
                cast = listOf(
                    CastMember(name = "Kyle Chandler"),
                    CastMember(name = "Aaron Pierre")
                ),
                commentCount = 122
            ),
            navController = rememberNavController()
        )
    }
}