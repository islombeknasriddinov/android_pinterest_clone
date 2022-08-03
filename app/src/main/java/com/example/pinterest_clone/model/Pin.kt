package com.example.pinterest_clone.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "pins_table")
 class Pin(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "id_user") val id_user: String,
    @ColumnInfo(name = "photo") var photo: String? = null,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "user_name") var user_name: String
)