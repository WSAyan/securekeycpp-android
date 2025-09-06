package com.wsayan.secureapikey.ui.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wsayan.secureapikey.domain.model.AnimeItem

@Composable
fun AnimeList(
    modifier: Modifier,
    animeList: List<AnimeItem>,
    onItemClick: (AnimeItem) -> Unit = {}
) {
    LazyColumn(modifier = modifier) {
        items(animeList) { anime ->
            AnimeListItem(
                title = anime.title,
                description = anime.synopsis,
                imageUrl = anime.imageUrl,
                score = anime.score,
                onClick = { onItemClick(anime) }
            )
        }
    }
}