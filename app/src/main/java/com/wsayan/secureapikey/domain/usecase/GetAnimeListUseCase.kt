package com.wsayan.secureapikey.domain.usecase

import com.wsayan.secureapikey.domain.ResourceState
import com.wsayan.secureapikey.domain.model.AnimeItem
import com.wsayan.secureapikey.domain.repository.AnimeRepository
import com.wsayan.secureapikey.domain.resourceFlow
import kotlinx.coroutines.flow.Flow

class GetAnimeListUseCase(private val animeRepository: AnimeRepository) {
    operator fun invoke(): Flow<ResourceState<List<AnimeItem>>> = resourceFlow {
        val animeList = animeRepository.getAnimeList()
        ResourceState.Success(animeList)
    }
}
