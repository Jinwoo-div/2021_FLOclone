package com.example.layout

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LikeTable")
data class Like (
    var userId :Int? = null,
    var albumId :Int? = null
) {
    @PrimaryKey(autoGenerate = true) var id :Int = 0
}
