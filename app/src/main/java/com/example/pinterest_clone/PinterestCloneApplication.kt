package com.example.pinterest_clone

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PinterestCloneApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}