package com.wsayan.secureapikey.data.network.dto

import com.google.gson.annotations.SerializedName

open class BaseResponseDto {
    @SerializedName("success")
    var success: Boolean? = null
    @SerializedName("message")
    var message: String? = null
}