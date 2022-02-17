package com.example.FLO_clone.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SongTable")
data class Song (
     var title: String = "",
     var singer: String = "",
     var second: Int = 0,
     var playTime: Int = 0,
     var isPlaying: Boolean = false,
     var music: String = "",
     var coverImg: Int? = 0,
     var isLike: Boolean = false
) {
     @PrimaryKey(autoGenerate = true) var id: Int = 0
}