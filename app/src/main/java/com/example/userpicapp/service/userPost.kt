package com.example.retrofitapp.Service

import com.google.gson.annotations.SerializedName

class userPost {
    @SerializedName("id")
    private var mId: Int? = null

    @SerializedName("date")
    private var mDate: String? = null

    @SerializedName("pics")
    private var mPic: ArrayList<String>? = null

    fun getId(): Int {
        return mId!!
    }

    fun setId(id: Int) {
        mId = id
    }

    fun getDate(): String {
        return mDate!!
    }

    fun setDate(date: String) {
        mDate = date
    }


    fun getPic(): ArrayList<String> {
        return mPic!!
    }

    fun setPic(pic: ArrayList<String>) {
        mPic = pic
    }

}