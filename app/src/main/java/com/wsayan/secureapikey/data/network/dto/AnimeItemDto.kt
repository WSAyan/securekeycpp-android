package com.wsayan.secureapikey.data.network.dto

import com.google.gson.annotations.SerializedName
import com.wsayan.secureapikey.AppException
import com.wsayan.secureapikey.domain.model.AnimeItem

data class AnimeItemDto(
    @SerializedName("title") var title: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("synopsis") var synopsis: String? = null,
    @SerializedName("score") var score: Double? = null,
    @SerializedName("images") var images: ImagesDto? = ImagesDto(),
) {
    fun toAnimeModel() = AnimeItem(
        title = this.title ?: throw AppException.DataIntegrity(),
        url = this.url ?: throw AppException.DataIntegrity(),
        synopsis = this.synopsis ?: "N/A",
        score = this.score?.toString() ?: "N/A",
        imageUrl = this.images?.jpg?.imageUrl ?: "N/A"
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