package com.example.userpicapp.util

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.activeandroid.Cache
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class ImageDownloader {

    fun downloadImage(picLink: String) {
        val imageURL: String = picLink
        val cw = ContextWrapper(Cache.getContext())
        val dir = cw.getDir("pics", Context.MODE_PRIVATE)
        val fileName = imageURL.substring(imageURL.lastIndexOf('/') + 1)
        Glide.with(Cache.getContext())
            .load(imageURL)
            .into(object : CustomTarget<Drawable?>() {

                override fun onLoadCleared(placeholder: Drawable?) {}

                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable?>?
                ) {
                    val bitmap = (resource as BitmapDrawable).bitmap
                    //Toast.makeText(getContext(), "Saving Image...", Toast.LENGTH_SHORT).show();
                    saveImage(bitmap, dir, fileName)
                }
            })
    }

    private fun saveImage(
        image: Bitmap,
        storageDir: File,
        imageFileName: String
    ) {
        val imageFile = File(storageDir, imageFileName)
        try {
            val fOut: OutputStream = FileOutputStream(imageFile)
            image.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
            fOut.close()
        } catch (e: Exception) {
            e.printStackTrace();
        }
    }

    fun getBitMap(pic: String): Bitmap? {
        var bitmap: Bitmap? = null
        val dir = pic.substring(pic.lastIndexOf('/')+1)
        try {
            val url = Cache.getContext()
                .applicationInfo.dataDir + "/app_pics/" + dir

            //VAlIDA SI LA IMAGEN EXISTE
            val imageProduct = File(url)
            if (imageProduct.exists()) {
                bitmap = BitmapFactory.decodeFile(url)
            }
        } catch (ex: java.lang.Exception) {
            bitmap = null
        }
        return bitmap
    }

    fun deletePicFolder() {
        val url = Cache.getContext()
            .applicationInfo.dataDir + "/app_pics/"
        val fileA = File(url)
        fileA.deleteRecursively()
    }

}