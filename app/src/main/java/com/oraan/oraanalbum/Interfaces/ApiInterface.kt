package com.oraan.oraanalbum.Interfaces

import com.oraan.oraanalbum.responses.AlbumResponse
import com.oraan.oraanalbum.responses.PhotoResponse
import com.oraan.oraanalbum.responses.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {


    @GET("users")
    fun getUsers(): Call<ArrayList<UserResponse>>


    @GET("albums")
    fun getAlbumById(
        @Query(
            "userId"
        ) userId: String
    ): Call<ArrayList<AlbumResponse>>

    @GET("photos")
    fun getPhotosByAlbumId(
        @Query(
            "albumId"
        ) albumId: String
    ): Call<ArrayList<PhotoResponse>>

}