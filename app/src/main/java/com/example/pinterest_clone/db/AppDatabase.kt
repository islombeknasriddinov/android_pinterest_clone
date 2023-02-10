package com.example.pinterest_clone.db

import android.content.Context
import androidx.room.*
import com.example.pinterest_clone.model.PhotoHomePage
import com.example.pinterest_clone.model.Pin
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
@Database(entities = [Pin::class], version = 1, exportSchema = false)
@TypeConverters(PhotoTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getPhotoHomeDao(): PhotoHomeDao

    companion object{
        @Volatile
        private var DB_INSTANCE: AppDatabase? = null

        fun getAppDBInstance(context: Context): AppDatabase {
            if (DB_INSTANCE == null){
                DB_INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "DB_PHOTO_HOME"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return DB_INSTANCE!!
        }
    }
}