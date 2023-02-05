package com.example.cinema.network

import com.example.cinema.data.Description
import com.example.cinema.data.Film
import com.example.cinema.data.ResponseFromServer
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService{
    @GET("api/v2.2/films/top")
    suspend fun getTopFilms(
        @Header("X-API-KEY") token: String = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b",
        @Query("type") type: String = "TOP_100_POPULAR_FILMS"
    ): Response<ResponseFromServer>

    @GET("api/v2.2/films/{id}")
    suspend fun getFilmById(
        @Header("X-API-KEY") token: String,
        @Path("id") id: Long
    ): Response<Description>
}
