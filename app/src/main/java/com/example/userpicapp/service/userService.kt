package com.example.retrofitapp.Service

import com.example.retrofitapp.Service.userResponse
import retrofit2.Call
import retrofit2.http.GET

interface userService {
    @GET("posts")
    fun getUserList() : Call<userResponse>
}