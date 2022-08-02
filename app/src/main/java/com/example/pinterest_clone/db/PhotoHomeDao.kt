package com.example.pinterest_clone.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pinterest_clone.model.Pin

@Dao
interface PhotoHomeDao {
    @Query("SELECT * FROM pins_table")
    suspend fun getPhotosFromDB(): List<Pin>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotosToDB(pin: Pin)

    @Query("DELETE FROM pins_table ")
    suspend fun deletePhotosFromDB()

}
