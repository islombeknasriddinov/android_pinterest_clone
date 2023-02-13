package com.example.pinterest_clone.utils

import android.app.DownloadManager
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Environment
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.pinterest_clone.fragment.parentHome.detail.DetailFragment
import com.google.android.material.snackbar.Snackbar
import java.io.File

object Utils {
    private val TAG = Utils::class.java.simpleName
    val DIR_NAME = "Pinterest Clone"

    fun saveImageToGallery(context: Context, url: String) {
        try {
            val filename = System.currentTimeMillis().toString() + ".jpeg"
            val direct = File(
                Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .absolutePath + "/" + DIR_NAME + "/"
            )

            if (!direct.exists()) {
                direct.mkdir()
            }

            val downloadUri = Uri.parse(url)
            val request = DownloadManager.Request(downloadUri)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(filename)
                .setMimeType("image/jpeg")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_PICTURES,
                    File.separator + DIR_NAME + File.separator.toString() + filename
                )

            (context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager).enqueue(request)

        } catch (e: Exception) {
            toaster(context, e.message ?: "")
            Logger.e(TAG, e.message.toString())
        }
    }

    fun toaster(context: Context, msg: String) {
        val toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        toast.show()
    }

    fun snackbar(view: View, text: String){
        val snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT)
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(Color.BLACK)
        val textView =
            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        textView.textSize = 16f
        val params = snackbarView.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        params.width = LinearLayout.LayoutParams.MATCH_PARENT
        snackbarView.layoutParams = params
        snackbar.show()
    }

}