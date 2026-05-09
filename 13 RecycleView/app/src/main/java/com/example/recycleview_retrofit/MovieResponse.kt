package com.example.recycleview_retrofit

data class MoviesResponse(
    val docs: List<Movie>
)

data class Movie(
    val id: Int,
    val name: String?,
    val enName: String?,
    val description: String?,
    val shortDescription: String?,
    val poster: Poster?,
    val alternativeName : String?
)

data class Poster(
    val url: String?
)