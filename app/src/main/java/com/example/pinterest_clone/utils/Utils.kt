package com.example.pinterest_clone.utils

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import java.io.File

class Utils {
    companion object{
        fun saveImageToGallery(context: Context, url: String){
                imageDownloader(context, url)
                val toast = Toast.makeText(context, "\t\tImage Saved to Gallery\t\t", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.TOP, 0, 0)
                toast.show()

        }

        val DIR_NAME = "MyPinterest"
        private fun imageDownloader(context: Context, url: String) {
            val filename = "filename.jpg"
            val downloadUrlOfImage = url
            val direct = File(Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .absolutePath + "/" + DIR_NAME + "/")

            if (!direct.exists()) {
                direct.mkdir()
                Log.d(DIR_NAME, "dir created for first time")
            }

            val downloadUri = Uri.parse(downloadUrlOfImage)
            val request = DownloadManager.Request(downloadUri)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(filename)
                .setMimeType("image/jpeg")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,
                    File.separator + DIR_NAME + File.separator.toString() + filename)

            (context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager).enqueue(request)

        }
    }
}