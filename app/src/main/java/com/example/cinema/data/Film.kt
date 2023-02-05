package com.example.cinema.data

import com.squareup.moshi.Json


class Film (
    @Json(name = "filmId")
    val filmID: Long,

    val nameRu: String,
    val year: String,
    val countries: List<Country>,
    val genres: List<Genre>,

    val description: String?,

    @Json(name = "posterUrl")
    val posterURL: String,

    @Json(name = "posterUrlPreview")
    val posterURLPreview: String)