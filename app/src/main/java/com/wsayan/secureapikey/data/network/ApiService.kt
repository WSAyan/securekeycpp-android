package com.wsayan.secureapikey.data.network

import com.wsayan.secureapikey.data.network.dto.AnimeResponseDto
import retrofit2.http.GET

interface ApiService {
    @GET("anime")
    suspend fun getAnimeList(): AnimeResponseDto
}