package com.example.pinterest_clone.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

open class BaseActivity : AppCompatActivity() {
    fun setTransparentStatusBarColor(
        context: Context,
        textColor: Int?,
        statusColor: Int?,
        lightStatus: Int?
    ) {
       window.decorView.systemUiVisibility =
            ContextCompat.getColor(context, textColor!!) //  set status text dark
       window.statusBarColor = ContextCompat.getColor(
            context,
            statusColor!!
        ) // set status bar color
        window.decorView.systemUiVisibility = lightStatus!!
    }
}