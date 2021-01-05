package com.example.userpicapp.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.userpicapp.R
import com.example.userpicapp.models.PicDB
import com.example.userpicapp.models.PostDB
import com.example.userpicapp.models.UserDB
import com.example.userpicapp.util.ImageDownloader
import com.example.userpicapp.util.Tools


class PostAdapter(applicationContext: Context, postList: MutableList<PostDB>) : RecyclerView.Adapter<PostAdapter.PostListViewHolder?>(), View.OnClickListener {

    private val listener: View.OnClickListener? = null
    private var mListener: AdapterView.OnItemClickListener? = null

    var context: Context? = null
    var postList: List<PostDB>? = null
    var inflater: LayoutInflater? = null
    var imageDownloader = ImageDownloader()

    private var user = UserDB()
    private var picDB = PicDB()

    init {
        this.context = applicationContext
        this.postList = postList
        inflater = LayoutInflater.from(applicationContext)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListViewHolder {
        //inflate view
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.adapter_post, parent, false)
        v.setOnClickListener(this)
        return PostListViewHolder(v, mListener as OnItemClickListener)
    }

    override fun getItemCount(): Int {
        return postList!!.size
    }

    override fun onBindViewHolder(holder: PostListViewHolder, position: Int) {

        user = user.getUserById(postList?.get(position)?.uid)

        try {
            val profilePicDir = user.profile_pic
            val profileImage= imageDownloader.getBitMap(profilePicDir!!)
            if (profileImage != null) {
                holder.imgVwUserProfilePic?.setImageBitmap(profileImage)

            } else {
                holder.imgVwUserProfilePic?.setImageBitmap(profileImage)
            }
        } catch (e: Exception) {
            e.printStackTrace();
        }

        holder.txtUserName!!.text = user.name
        holder.txtUserEmail!!.text = user.email
        val date = Tools().getDate(postList?.get(position)?.date.toString())
        holder.txtPostDate!!.text = date

        val postId = postList?.get(position)?.idPost
        val picList = picDB.getPicListByPostId(postId!!)
        //valida cantidad de imagenes y de la misma manera muestra el layout respectivo
        when {
            picList.size==1 -> {
                holder.lytOnePic!!.visibility = View.VISIBLE
                val pic = picList[0]
                try {
                    val picDir = pic.pic
                    val picImage= imageDownloader.getBitMap(picDir!!)
                    if (picImage != null) {
                        holder.imgVwOnePic?.setImageBitmap(picImage)

                    } else {
                        holder.imgVwOnePic?.setImageBitmap(picImage)
                    }
                } catch (e: Exception) {
                    e.printStackTrace();
                }
            }
            picList.size==2 -> {
                holder.lytTwoPic!!.visibility = View.VISIBLE
                var pic = picList[0]
                try {
                    val picDir = pic.pic
                    val picImage= imageDownloader.getBitMap(picDir!!)
                    if (picImage != null) {
                        holder.imgVwFirstTwoPic?.setImageBitmap(picImage)

                    } else {
                        holder.imgVwFirstTwoPic?.setImageBitmap(picImage)
                    }
                } catch (e: Exception) {
                    e.printStackTrace();
                }
                pic = picList[1]
                try {
                    val picDir = pic.pic
                    val picImage= imageDownloader.getBitMap(picDir!!)
                    if (picImage != null) {
                        holder.imgVwSecondTwoPic?.setImageBitmap(picImage)

                    } else {
                        holder.imgVwSecondTwoPic?.setImageBitmap(picImage)
                    }
                } catch (e: Exception) {
                    e.printStackTrace();
                }
            }
            picList.size==3 -> {
                holder.lytThreePic!!.visibility = View.VISIBLE
                var pic = picList[0]
                try {
                    val picDir = pic.pic
                    val picImage= imageDownloader.getBitMap(picDir!!)
                    if (picImage != null) {
                        holder.imgVwFirstThreePic?.setImageBitmap(picImage)

                    } else {
                        holder.imgVwFirstThreePic?.setImageBitmap(picImage)
                    }
                } catch (e: Exception) {
                    e.printStackTrace();
                }
                pic = picList[1]
                try {
                    val picDir = pic.pic
                    val picImage= imageDownloader.getBitMap(picDir!!)
                    if (picImage != null) {
                        holder.imgVwSecondThreePic?.setImageBitmap(picImage)

                    } else {
                        holder.imgVwSecondThreePic?.setImageBitmap(picImage)
                    }
                } catch (e: Exception) {
                    e.printStackTrace();
                }
                pic = picList[2]
                try {
                    val picDir = pic.pic
                    val picImage= imageDownloader.getBitMap(picDir!!)
                    if (picImage != null) {
                        holder.imgVwThirdThreePic?.setImageBitmap(picImage)

                    } else {
                        holder.imgVwThirdThreePic?.setImageBitmap(picImage)
                    }
                } catch (e: Exception) {
                    e.printStackTrace();
                }
            }
            picList.size>3 -> {
//                holder.lytFourPic!!.visibility = View.VISIBLE
//                val pic = picList[0]
//                try {
//                    val picDir = pic.pic
//                    val picImage= imageDownloader.getBitMap(picDir!!)
//                    if (picImage != null) {
//                        holder.imgVwFirstFourPic?.setImageBitmap(picImage)
//
//                    } else {
//                        holder.imgVwFirstFourPic?.setImageBitmap(picImage)
//                    }
//                } catch (e: Exception) {
//                    e.printStackTrace();
//                }
            }
        }

    }

    override fun onClick(view: View?) {
        listener?.onClick(view)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    interface OnItemClickListener : AdapterView.OnItemClickListener {
        fun onClick(position: Int, v: View, bm : Bitmap)
    }

    class PostListViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {

        var imgVwUserProfilePic: ImageView? = null
        var txtUserName: TextView? = null
        var txtUserEmail: TextView? = null
        var txtPostDate: TextView? = null

        var lytOnePic: LinearLayout? = null
        var imgVwOnePic: ImageView? = null

        var lytTwoPic: LinearLayout? = null
        var imgVwFirstTwoPic: ImageView? = null
        var imgVwSecondTwoPic: ImageView? = null

        var lytThreePic: LinearLayout? = null
        var imgVwFirstThreePic: ImageView? = null
        var lytSubThreePic: LinearLayout? = null
        var imgVwSecondThreePic: ImageView? = null
        var imgVwThirdThreePic: ImageView? = null

        var lytFourPic: LinearLayout? = null
        var imgVwFirstFourPic: ImageView? = null
        var rcvPic: RecyclerView? = null

        init {

            imgVwUserProfilePic  = itemView.findViewById(R.id.imgVwUserProfilePic )
            txtUserName  = itemView.findViewById(R.id.txtUserName )
            txtUserEmail  = itemView.findViewById(R.id.txtUserEmail )
            txtPostDate = itemView.findViewById(R.id.txtPostDate )

            lytOnePic  = itemView.findViewById(R.id.lytOnePic )
            imgVwOnePic  = itemView.findViewById(R.id. imgVwOnePic)

            lytTwoPic  = itemView.findViewById(R.id. lytTwoPic)
            imgVwFirstTwoPic  = itemView.findViewById(R.id.imgVwFirstTwoPic )
            imgVwSecondTwoPic  = itemView.findViewById(R.id. imgVwSecondTwoPic)

            lytThreePic  = itemView.findViewById(R.id. lytThreePic)
            imgVwFirstThreePic  = itemView.findViewById(R.id.imgVwFirstThreePic )
            lytSubThreePic  = itemView.findViewById(R.id. lytSubThreePic)
            imgVwSecondThreePic  = itemView.findViewById(R.id.imgVwSecondThreePic )
            imgVwThirdThreePic  = itemView.findViewById(R.id. imgVwThirdThreePic)

            lytFourPic  = itemView.findViewById(R.id.lytFourPic )
            imgVwFirstFourPic  = itemView.findViewById(R.id.imgVwFirstFourPic )
            rcvPic  = itemView.findViewById(R.id.rcvPic )

            imgVwOnePic!!.setOnClickListener(View.OnClickListener { v ->
                val position = adapterPosition

                val bm = (imgVwOnePic!!.drawable as BitmapDrawable).bitmap

                if (position != RecyclerView.NO_POSITION) {
                    listener.onClick(position, v, bm)
                }
            })

            imgVwFirstTwoPic!!.setOnClickListener(View.OnClickListener { v ->
                val position = adapterPosition

                val bm = (imgVwFirstTwoPic!!.drawable as BitmapDrawable).bitmap

                if (position != RecyclerView.NO_POSITION) {
                    listener.onClick(position, v, bm)
                }
            })

            imgVwSecondTwoPic!!.setOnClickListener(View.OnClickListener { v ->
                val position = adapterPosition

                val bm = (imgVwSecondTwoPic!!.drawable as BitmapDrawable).bitmap

                if (position != RecyclerView.NO_POSITION) {
                    listener.onClick(position, v, bm)
                }
            })

            imgVwFirstThreePic!!.setOnClickListener(View.OnClickListener { v ->
                val position = adapterPosition

                val bm = (imgVwFirstThreePic!!.drawable as BitmapDrawable).bitmap

                if (position != RecyclerView.NO_POSITION) {
                    listener.onClick(position, v, bm)
                }
            })

            imgVwSecondThreePic!!.setOnClickListener(View.OnClickListener { v ->
                val position = adapterPosition

                val bm = (imgVwSecondThreePic!!.drawable as BitmapDrawable).bitmap

                if (position != RecyclerView.NO_POSITION) {
                    listener.onClick(position, v, bm)
                }
            })

            imgVwThirdThreePic!!.setOnClickListener(View.OnClickListener { v ->
                val position = adapterPosition

                val bm = (imgVwThirdThreePic!!.drawable as BitmapDrawable).bitmap

                if (position != RecyclerView.NO_POSITION) {
                    listener.onClick(position, v, bm)
                }
            })

            imgVwFirstFourPic!!.setOnClickListener(View.OnClickListener { v ->
                val position = adapterPosition

                val bm = (imgVwFirstFourPic!!.drawable as BitmapDrawable).bitmap

                if (position != RecyclerView.NO_POSITION) {
                    listener.onClick(position, v, bm)
                }
            })


        }
    }
}