package com.example.layout

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AlbumTable")
data class Albums (
    @PrimaryKey(autoGenerate = false) var id: Int = 0,
    var title: String = "",
    var singer: String = "",
    var coverImg: Int? = null
)