package com.example.retrofitapp.Service

import com.example.retrofitapp.Service.user
import com.google.gson.annotations.SerializedName

class userResponse {
    @SerializedName("user")
    var user = ArrayList<user>()
}