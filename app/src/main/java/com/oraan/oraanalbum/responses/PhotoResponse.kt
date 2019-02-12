package com.oraan.oraanalbum.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PhotoResponse(
    @SerializedName("albumId") @Expose var albumId: String?,
    @SerializedName("id") @Expose var id: String?,
    @SerializedName("title") @Expose var title: String?,
    @SerializedName("url") @Expose var url: String?,
    @SerializedName("thumbnailUrl") @Expose var thumbnailUrl: String?
)