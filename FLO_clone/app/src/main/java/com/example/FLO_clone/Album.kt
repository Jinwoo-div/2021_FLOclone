package com.example.FLO_clone

data class Album (
    var title: String? = "",
    var singer: String? = "",
    var coverImg: Int? = null,
    var songs: ArrayList<String>?= null
)