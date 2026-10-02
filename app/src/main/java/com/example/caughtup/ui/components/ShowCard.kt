package com.example.caughtup.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

// Simple placeholder data model, swap for a real data class once Room/TMDB are wired up.
// Shared by both the Shows and Movies screens — that's why the field names are generic
// rather than show-specific: a movie has no season/episode, but it still needs a small
// top pill (year · runtime instead of "S1E4 · Episode Premiere") and a caption line
// (a synopsis instead of "starts in 2h 15m").
data class ShowPreview(
    val title: String,
    // Small pill top-left over the poster.
    // Shows: "S1E4 · Episode Premiere"   Movies: "1961 · 1h 55m"
    val topTag: String,
    // Line under the title.
    // Shows: "The Weenie starts in 2h 15m"   Movies: a short synopsis
    val caption: String,
    // TMDB gives you a partial path like "/abc123.jpg" — this is the full URL built from it.
    // Leave null while you don't have a real API result yet.
    val posterUrl: String? = null,
    // Local placeholder poster from res/drawable (e.g. R.drawable.lanterns_poster).
    // Use this OR posterUrl, not both — posterRes wins if both are set.
    val posterRes: Int? = null,
    // TODO: wire this to real watched-state once there's a data layer.
    // For now it's just display-only — tapping it doesn't do anything yet.
    val isWatched: Boolean = false
)

@Composable
fun ShowCard(show: ShowPreview, onClick: () -> Unit = {}) {
    // TODO: measure the real card height off the Figma frame (Inspect panel) and swap this in.
    val cardShape = RoundedCornerShape(16.dp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(cardShape) // clip BEFORE clickable so the tap ripple stays inside the rounded corners
            .clickable(onClick = onClick)
            .background(Color(0xFF1B1B2F))
    ) {
        // Full-bleed poster behind everything else — this is what makes it match Figma,
        // instead of a small thumbnail sitting next to the text.
        if (show.posterRes != null) {
            Image(
                painter = painterResource(id = show.posterRes),
                contentDescription = show.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            AsyncImage(
                model = show.posterUrl,
                contentDescription = show.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Dark gradient over the bottom half so white text stays readable on any poster.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.85f)),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )

        // Top row: episode tag pill (left) + watched checkmark (right).
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = show.topTag,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.45f), shape = RoundedCornerShape(6.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )

            Box(
                modifier = Modifier
                    .size(28.dp)
                    .background(Color.Black.copy(alpha = 0.45f), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = if (show.isWatched) "Watched" else "Mark as watched",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        // Title + timing text, pinned to the bottom-left over the gradient.
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
        ) {
            Text(
                text = show.title,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = show.caption,
                color = Color(0xFFD0D0D0),
                fontSize = 13.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}