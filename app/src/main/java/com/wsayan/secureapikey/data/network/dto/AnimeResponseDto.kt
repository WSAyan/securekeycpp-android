package com.wsayan.secureapikey.data.network.dto

import com.google.gson.annotations.SerializedName

data class AnimeResponseDto(
    @SerializedName("data") var data: List<AnimeItemDto> = listOf()
): BaseResponseDto() {
    fun toAnimeItemList() = data.map { it.toAnimeModel() }
}
