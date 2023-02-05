package com.example.cinema.data


data class ResponseFromServer (
    val pagesCount: Long,
    val films: List<Film>
)