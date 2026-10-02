package com.example.caughtup.data

import com.example.caughtup.ui.components.CastMember
import com.example.caughtup.ui.components.MovieDetail

// Same idea as SampleShowDetails.kt, but for the Movies tab. One entry per
// sample movie card currently shown on MoviesScreen, so every card is clickable.

private val breakfastAtTiffanys = MovieDetail(
    title = "Breakfast at Tiffany's",
    rating = 4.2,
    runtimeMinutes = 115,
    year = 1961,
    genres = listOf("Romance", "Comedy", "Drama"),
    synopsis = "A young New York socialite becomes interested in a young man who has moved " +
            "into her apartment building, but her past gets in the way.",
    cast = listOf(
        CastMember(name = "Audrey Hepburn"),
        CastMember(name = "George Peppard")
    )
)

private val childrenOfMen = MovieDetail(
    title = "Children of Men",
    rating = 4.6,
    runtimeMinutes = 109,
    year = 2006,
    genres = listOf("Sci-Fi", "Thriller", "Drama"),
    synopsis = "In 2027, in a chaotic world in which women have become somehow infertile, a " +
            "former activist agrees to help transport a miraculously pregnant woman to a sanctuary.",
    cast = listOf(
        CastMember(name = "Clive Owen"),
        CastMember(name = "Julianne Moore"),
        CastMember(name = "Michael Caine")
    )
)

private val dunePartTwo = MovieDetail(
    title = "Dune: Part Two",
    rating = 4.8,
    runtimeMinutes = 166,
    year = 2024,
    genres = listOf("Sci-Fi", "Adventure", "Drama"),
    synopsis = "Paul Atreides unites with the Fremen while on a path of revenge against the " +
            "conspirators who destroyed his family, facing a choice between the love of his life " +
            "and the fate of the known universe.",
    cast = listOf(
        CastMember(name = "Timothee Chalamet"),
        CastMember(name = "Zendaya"),
        CastMember(name = "Rebecca Ferguson"),
        CastMember(name = "Javier Bardem")
    ),
    isInWatchlist = true
)

private val allSampleMovieDetails = listOf(breakfastAtTiffanys, childrenOfMen, dunePartTwo)

fun findMovieDetailByTitle(title: String?): MovieDetail? {
    if (title == null) return null
    return allSampleMovieDetails.firstOrNull { it.title.startsWith(title, ignoreCase = true) }
}