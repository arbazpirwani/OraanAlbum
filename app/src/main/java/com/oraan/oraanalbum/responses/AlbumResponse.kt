package com.oraan.oraanalbum.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AlbumResponse(
    @SerializedName("userId") @Expose var userId: String?,
    @SerializedName("id") @Expose var id: String?,
    @SerializedName("title") @Expose var title: String?
){
     var photoResponseList: ArrayList<PhotoResponse> = java.util.ArrayList()

}