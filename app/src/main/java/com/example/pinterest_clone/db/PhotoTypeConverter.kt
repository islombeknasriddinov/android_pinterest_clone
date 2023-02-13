package com.example.pinterest_clone.db

import androidx.room.TypeConverter
import com.example.pinterest_clone.model.PhotoHomePage
import com.google.gson.Gson

class PhotoTypeConverter {
    @TypeConverter
    fun fromPhoto(photoItem: PhotoHomePage): String {
        return Gson().toJson(photoItem)
    }

    @TypeConverter
    fun toPhoto(json: String): PhotoHomePage {
        return Gson().fromJson(json, PhotoHomePage::class.java)
    }
}