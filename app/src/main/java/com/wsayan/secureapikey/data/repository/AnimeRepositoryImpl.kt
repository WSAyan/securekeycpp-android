package com.wsayan.secureapikey.data.repository

import com.wsayan.secureapikey.data.network.ApiService
import com.wsayan.secureapikey.data.network.safeApiCall
import com.wsayan.secureapikey.domain.model.AnimeItem
import com.wsayan.secureapikey.domain.repository.AnimeRepository

class AnimeRepositoryImpl(private val apiService: ApiService) : AnimeRepository {
    override suspend fun getAnimeList(): List<AnimeItem> {
        return safeApiCall { apiService.getAnimeList().toAnimeItemList() }
    }
}

