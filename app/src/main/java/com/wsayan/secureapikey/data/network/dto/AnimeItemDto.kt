package com.wsayan.secureapikey.data.network.dto

import com.google.gson.annotations.SerializedName
import com.wsayan.secureapikey.AppException
import com.wsayan.secureapikey.domain.model.AnimeItem

data class AnimeItemDto(
    @SerializedName("title") var title: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("images") var images: ImagesDto? = ImagesDto(),
) {
    fun toAnimeModel() = AnimeItem(
        title = this.title ?: throw AppException.DataIntegrity(),
        url = this.url ?: throw AppException.DataIntegrity(),
        imageUrl = this.images?.jpg?.imageUrl ?: throw AppException.DataIntegrity()
    )
}

data class ImagesDto(
    @SerializedName("jpg") var jpg: ImageUrls? = ImageUrls(),
    @SerializedName("webp") var webp: ImageUrls? = ImageUrls()

)

data class ImageUrls(
    @SerializedName("image_url") var imageUrl: String? = null,
    @SerializedName("small_image_url") var smallImageUrl: String? = null,
    @SerializedName("large_image_url") var largeImageUrl: String? = null
)