package com.example.retrofitapp.Service

import com.google.gson.annotations.SerializedName

class user {
    @SerializedName("uid")
    var uid = ""
    @SerializedName("name")
    var name = ""
    @SerializedName("email")
    var email = ""
    @SerializedName("profile_pic")
    var profile_pic = ""

    @SerializedName("post")
    private var mPost: userPost? = null

    fun getPost(): userPost? {
        return mPost
    }

    fun setPost(post: userPost) {
        mPost = post
    }
}