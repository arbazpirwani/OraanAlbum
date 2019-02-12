package com.oraan.oraanalbum.utils


import com.oraan.oraanalbum.Interfaces.ApiInterface
import com.oraan.oraanalbum.remote.RetrofitClient

object ServiceUtils {
    
    val api: ApiInterface
        get() = RetrofitClient.getClient(Constant.BASE_URL).create(ApiInterface::class.java)
}


