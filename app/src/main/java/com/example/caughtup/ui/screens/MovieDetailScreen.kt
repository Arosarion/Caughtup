package com.example.caughtup.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.caughtup.ui.components.CastMember
import com.example.caughtup.ui.components.MovieDetail
import com.example.caughtup.ui.theme.HighlightedColor
import com.example.caughtup.ui.theme.SecWordColor

// Mirrors ShowDetailScreen.kt's structure closely, minus anything season/episode
// related (no season selector, no Episodes list) and with the episode page's
// "Rate the Episode" pills reused here as "Rate the Movie". Sub-composables kept
// private/local to this file, same pattern used for ShowDetailScreen.
@Composable
fun MovieDetailScreen(movie: MovieDetail?, navController: NavController) {
    if (movie == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Movie not found", color = Color.White)
        }
        return
    }

    var isInWatchlist by remember { mutableStateOf(movie.isInWatchlist) }
    var isWatched by remember { mutableStateOf(movie.isWatched) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF13131F))
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) {
                if (movie.heroRes != null) {
                    Image(
                        painter = painterResource(id = movie.heroRes),
                        contentDescription = movie.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    AsyncImage(
                        model = movie.heroUrl,
                        contentDescription = movie.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color(0xCC13131F))
                            )
                        )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CircleIconButton(
                        icon = Icons.Filled.ArrowBack,
                        tint = Color.White,
                        onClick = { navController.popBackStack() }
                    )
                    CircleIconButton(
                        icon = if (isInWatchlist) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                        tint = if (isInWatchlist) HighlightedColor else Color.White,
                        onClick = { isInWatchlist = !isInWatchlist }
                    )
                }
            }
        }

        item {
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
                Text(
                    text = movie.title,
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = HighlightedColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = " ${movie.rating} · ${movie.runtimeMinutes} min · ${movie.year}",
                        color = SecWordColor,
                        fontSize = 14.sp
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    movie.genres.forEach { genre -> GenreChip(text = genre) }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ActionButton(
                        text = "In Watchlist",
                        icon = if (isInWatchlist) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                        isActive = isInWatchlist,
                        modifier = Modifier.weight(1f),
                        onClick = { isInWatchlist = !isInWatchlist }
                    )
                    ActionButton(
                        text = "Mark Watched",
                        icon = if (isWatched) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        isActive = isWatched,
                        modifier = Modifier.weight(1f),
                        onClick = { isWatched = !isWatched }
                    )
                }

                Text(
                    text = "Synopsis",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 24.dp)
                )
                Text(
                    text = movie.synopsis,
                    color = SecWordColor,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )

                if (movie.cast.isNotEmpty()) {
                    Text(
                        text = "Cast",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 24.dp)
                    )
                }
            }
        }

        if (movie.cast.isNotEmpty()) {
            item {
                LazyRow(
                    modifier = Modifier.padding(start = 20.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(movie.cast) { member -> CastMemberItem(member = member) }
                }
            }
        }

        item {
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
                Text(
                    text = "Rate the Movie",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("Bad", "OK", "Good", "Great", "Perfect").forEach { label ->
                        RatingPill(label = label, modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun CircleIconButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    tint: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color(0x66000000)),
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = onClick) {
            Icon(imageVector = icon, contentDescription = null, tint = tint)
        }
    }
}

@Composable
private fun GenreChip(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(Color(0xFF1F1F33))
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        Text(text = text, color = SecWordColor, fontSize = 13.sp)
    }
}

@Composable
private fun ActionButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isActive: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (isActive) HighlightedColor.copy(alpha = 0.15f) else Color(0xFF1F1F33))
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isActive) HighlightedColor else SecWordColor,
            modifier = Modifier.size(18.dp)
        )
        Text(
            text = text,
            color = if (isActive) HighlightedColor else SecWordColor,
            fontSize = 13.sp,
            modifier = Modifier.padding(start = 6.dp)
        )
    }
}

@Composable
private fun CastMemberItem(member: CastMember) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (member.photoRes != null) {
            Image(
                painter = painterResource(id = member.photoRes),
                contentDescription = member.name,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else if (member.photoUrl != null) {
            AsyncImage(
                model = member.photoUrl,
                contentDescription = member.name,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF1F1F33)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = member.name.firstOrNull()?.toString() ?: "?",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Text(
            text = member.name,
            color = SecWordColor,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
private fun RatingPill(label: String, modifier: Modifier = Modifier) {
    var selected by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(if (selected) HighlightedColor else Color(0xFF1F1F33))
            .clickable { selected = !selected }
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = if (selected) Color.Black else SecWordColor,
            fontSize = 12.sp
        )
    }
}