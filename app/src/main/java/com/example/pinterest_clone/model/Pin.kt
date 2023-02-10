package com.example.pinterest_clone.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "pins_table",indices = [Index(value = ["photo"], unique = true)])
 class Pin(
   @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
   @ColumnInfo(name = "id_user") val id_user: String,
   @ColumnInfo(name = "photo") var photo: String? = null,
   @ColumnInfo(name = "description") var description: String? = null,
   @ColumnInfo(name = "user_name") var user_name: String? = null,
   @ColumnInfo(name = "isLiked") var isLiked: Boolean
) : Serializable