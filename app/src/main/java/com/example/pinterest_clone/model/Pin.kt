package com.example.pinterest_clone.model

import androidx.room.*
import java.io.Serializable

@Entity(tableName = "pins_table"/*, indices = [Index(value = ["photoHome"], unique = true)]*/)
class Pin(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var photoHome: PhotoHomePage
) : Serializable