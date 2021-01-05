package com.example.userpicapp.activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.userpicapp.R
import com.example.userpicapp.adapters.PostAdapter
import com.example.userpicapp.models.ClearDB
import com.example.userpicapp.models.PicDB
import com.example.userpicapp.models.PostDB
import com.example.userpicapp.models.UserDB
import com.example.userpicapp.util.ImageDownloader
import com.example.userpicapp.util.OnSwipeTouchListener

class MainActivity : AppCompatActivity() {

    //Variables globales

    private lateinit  var context: Context

    //define la funcion de refrescar la informacion
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    //objeto de post
    private lateinit var post: PostDB
    //lista de post
    private lateinit var postList: MutableList<PostDB>

    private lateinit var rcvPost: RecyclerView
    private lateinit var postListAdapter: PostAdapter

    //varibles de manejo para el popup de la imagen
    private var mPopUpPicItem: PopupWindow? = null
    var popUpPicItemView: View? = null
    private lateinit var imgVwPopUpPic: ImageView
    private lateinit var rLytPic: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context=this

        post = PostDB()
        //carga la lista de todos los post
        postList = post.postList

        //inicia los componentes visuales
        initComponents()

        //crea el adapater de los post
        buildPostAdapter()

        //inicia los listeners
        initListener()

        //inicia la funcion de refrescar la información
        refreshApp()
    }

    private fun initComponents() {

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        rcvPost = findViewById(R.id.rcvPost)
        val linearLayoutManager = LinearLayoutManager(context)
        rcvPost.layoutManager = linearLayoutManager
    }

    private fun buildPostAdapter() {

        postListAdapter = PostAdapter(applicationContext, postList)
        rcvPost.adapter = postListAdapter
    }

    private fun initListener() {

        //crea el escucha para abrir la imagen en el popup
        postListAdapter.setOnItemClickListener( object: PostAdapter.OnItemClickListener {
            override fun onClick(position: Int, v: View, bm: Bitmap) {
                goPopUp(bm)
            }

            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }

        })

    }

    private fun goPopUp(bm: Bitmap) {
        popUpPicItemView = layoutInflater.inflate(R.layout.popup_pic,
            null) // inflating popup layout

        val width = (resources.displayMetrics.widthPixels * 1).toInt() // 0.90 = 90% of width size
        //val height = (resources.displayMetrics.heightPixels * 1).toInt() // 0.90 = 90% of hight size

        //val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.MATCH_PARENT

        mPopUpPicItem = PopupWindow(popUpPicItemView,
            width,  //width value
            height,  //height value
            true) // Creation of popup

        mPopUpPicItem!!.animationStyle = android.R.style.Animation_Dialog
        mPopUpPicItem!!.showAtLocation(popUpPicItemView, Gravity.CENTER, 0, 0)

        mPopUpPicItem!!.isOutsideTouchable = false
        mPopUpPicItem!!.isFocusable = false

        //vincula los componentes con la vista
        initPicPopUpComponents(bm)

        //inicia los diferentes escuchas de la vista
        initPicPopUpListener()
    }

    private fun initPicPopUpListener() {

        //escucha para los gestos del usuario
        rLytPic.setOnTouchListener(object : OnSwipeTouchListener(this@MainActivity) {
            override fun onSwipeLeft() {
                super.onSwipeLeft()
//                Toast.makeText(this@MainActivity, "Swipe Left gesture detected",Toast.LENGTH_SHORT).show()
            }
            override fun onSwipeRight() {
                super.onSwipeRight()
//                Toast.makeText(this@MainActivity, "Swipe Right gesture detected", Toast.LENGTH_SHORT).show()
            }
            override fun onSwipeUp() {
                super.onSwipeUp()
//                Toast.makeText(this@MainActivity, "Swipe up gesture detected", Toast.LENGTH_SHORT).show()
            }
            override fun onSwipeDown() {
                super.onSwipeDown()
//                Toast.makeText(this@MainActivity, "Swipe down gesture detected", Toast.LENGTH_SHORT).show()
                mPopUpPicItem!!.dismiss()
            }
        })

    }

    private fun initPicPopUpComponents(bm: Bitmap) {

        rLytPic = popUpPicItemView!!.findViewById(R.id.rLytPic )

        imgVwPopUpPic = popUpPicItemView!!.findViewById(R.id.imgVwPic )
        imgVwPopUpPic.setImageBitmap(bm)

    }

    private fun refreshApp() {

        swipeRefreshLayout.setOnRefreshListener {
            goSplashActivity()
        }

    }

    private fun goSplashActivity() {
        //borra la base de datos
        ClearDB()
        swipeRefreshLayout.isRefreshing = false
        this.startActivity(Intent(this, SplashActivity::class.java))
        finish()
    }
}