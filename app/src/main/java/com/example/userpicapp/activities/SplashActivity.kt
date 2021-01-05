package com.example.userpicapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.activeandroid.ActiveAndroid
import com.example.retrofitapp.Service.user
import com.example.retrofitapp.Service.userResponse
import com.example.retrofitapp.Service.userService
import com.example.userpicapp.R
import com.example.userpicapp.models.PicDB
import com.example.userpicapp.models.PostDB
import com.example.userpicapp.models.UserDB
import com.example.userpicapp.util.ImageDownloader
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SplashActivity : AppCompatActivity() {

    private lateinit var context: Context

    private lateinit var userDB : UserDB
    private lateinit var postDB : PostDB
    private lateinit var picDB : PicDB
    private lateinit var  imageDownloader : ImageDownloader

    //lista de usuarios
    private lateinit var userList: MutableList<UserDB>
    //lista de post
    private lateinit var postList: MutableList<PostDB>
    //lista de pics
    private lateinit var picList: MutableList<PicDB>

    //define la funcion de refrescar la informacion
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        context = this

        userDB = UserDB()
        postDB = PostDB()
        picDB = PicDB()
        imageDownloader = ImageDownloader()
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        //initialize database
        ActiveAndroid.initialize(this)

        //valida si ya hay informacion
        if (info()){
            goMainActivity()
        }else{
            downloadUserInfo()
        }

    }

    private fun info(): Boolean {
        userList = userDB.userList
        postList = postDB.postList
        picList = picDB.picList

        return !(userList.isEmpty()||postList.isEmpty()||picList.isEmpty())

    }

    private fun downloadUserInfo() {
        val serviceURL = "https://mock.koombea.io/mt/api/"

        val retrofit = Retrofit.Builder()
            .baseUrl(serviceURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(userService::class.java)

        val call = service.getUserList()
        call.enqueue(object : Callback<userResponse> {
            override fun onResponse(call: Call<userResponse>, response: Response<userResponse>) {
                try {
                    if (response.code() == 200) {

                        if (swipeRefreshLayout.isRefreshing){
                            swipeRefreshLayout.isRefreshing = false
                        }

                        val userResponse = response.body()!!
                        for( user in userResponse.user){
                            Log.v("Name", user.name)
                            Log.v("Email", user.email)
                            Log.v("uid", user.uid)
                            Log.v("profile pic", user.profile_pic)

                            userDB.saveUser(user.uid, user.name, user.email, user.profile_pic)

                            val userPost = user.getPost()
                            val postId =  userPost!!.getId()
                            val postDate = userPost.getDate()

                            postDB.savePost(postId, postDate, user.uid)

                            val postPics = userPost.getPic()

                            Log.v("post Id", postId.toString())
                            Log.v("postDate", postDate)
                            for (pic in postPics){
                                Log.v("picLink", pic)
                                picDB.savePic(postId, pic)

                            }
                        }
                        userList = userDB.userList
                        postList = postDB.postList
                        picList = picDB.picList
                        //descarga y guarda las imagenes de manera local
                        for (user in userList){
                            try {
                                imageDownloader.downloadImage(user.profile_pic)
                            } catch (ex: Exception) {
                                Log.e("profile pic", "Error al descargar imagenes $ex")
                            }
                        }
                        for (pic in picList){
                            try {
                                imageDownloader.downloadImage(pic.pic)
                            } catch (ex: Exception) {
                                Log.e("post pic", "Error al descargar imagenes $ex")
                            }
                        }
                        if (info()){
                            goMainActivity()
                        }else{
                            Toast.makeText(applicationContext, "Empty server response... swipe down to refresh", Toast.LENGTH_LONG).show()
                            refreshApp()
                        }
                    }else{
                        Toast.makeText(applicationContext, "Bad server response", Toast.LENGTH_LONG).show();
                    }
                } catch (e: java.lang.Exception) {
                    Toast.makeText(applicationContext, "Download or save error", Toast.LENGTH_LONG).show();
                    e.printStackTrace()
                }

            }
            override fun onFailure(call: Call<userResponse>, t: Throwable) {
                Log.v("MainActivity", t.toString())
            }
        })
    }

    fun goMainActivity() {

        this.startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun refreshApp() {

        swipeRefreshLayout.setOnRefreshListener {
            downloadUserInfo()
        }
    }
}