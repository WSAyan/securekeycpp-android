package com.wsayan.secureapikey.domain.repository

import com.wsayan.secureapikey.domain.model.AnimeItem

interface AnimeRepository {
    suspend fun getAnimeList(): List<AnimeItem>
}