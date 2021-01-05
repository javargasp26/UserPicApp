package com.example.userpicapp.adapters

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.userpicapp.R
import com.example.userpicapp.models.UserDB
import com.example.userpicapp.util.ImageDownloader

class UserAdapter (applicationContext: Context, userList: MutableList<UserDB>) : RecyclerView.Adapter<UserAdapter.UserListViewHolder?>(), View.OnClickListener {

    private val listener: View.OnClickListener? = null
    private var mListener: AdapterView.OnItemClickListener? = null

    var context: Context? = null
    var userList: List<UserDB>? = null
    var inflater: LayoutInflater? = null
    var imageDownloader = ImageDownloader()


    init {
        this.context = applicationContext
        this.userList = userList
        inflater = LayoutInflater.from(applicationContext)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserAdapter.UserListViewHolder {
        //inflate view
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.adapter_user, parent, false)
        v.setOnClickListener(this)
        return UserListViewHolder(v, mListener as OnItemClickListener)
    }

    override fun getItemCount(): Int {
        return userList!!.size
    }

    override fun onBindViewHolder(holder: UserAdapter.UserListViewHolder, position: Int) {
        try {
            val profilePicDir = userList?.get(position)?.profile_pic
            val profileImage= imageDownloader.getBitMap(profilePicDir!!)
            if (profileImage != null) {
                holder.imgVwUserProfilePic?.setImageBitmap(profileImage)

            } else {
                holder.imgVwUserProfilePic?.setImageBitmap(profileImage)
            }
        } catch (e: Exception) {
            e.printStackTrace();
        }
    }

    override fun onClick(view: View?) {
        listener?.onClick(view)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    interface OnItemClickListener : AdapterView.OnItemClickListener {
        fun onClick(position: Int)
    }

    class UserListViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {

        var imgVwUserProfilePic: ImageView? = null

        init {

            imgVwUserProfilePic  = itemView.findViewById(R.id.imgVwUserProfilePic )

        }
    }
}