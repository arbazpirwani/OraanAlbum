package com.oraan.oraanalbum.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id") @Expose var id: String?,
    @SerializedName("name") @Expose var name: String?,
    @SerializedName("username") @Expose var username: String?,
    @SerializedName("email") @Expose var email: String?
)