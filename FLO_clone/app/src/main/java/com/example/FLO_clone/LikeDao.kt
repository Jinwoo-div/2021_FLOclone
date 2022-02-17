package com.example.FLO_clone

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LikeDao {
    @Insert
    fun insert(like: Like)

    @Query("DELETE FROM LIKETABLE WHERE userId = :id AND albumId = :album")
    fun delete(id: Int?, album: Int?)

    @Query("SELECT * FROM LikeTable WHERE userId = :id")
    fun getLikedAlbum(id: Int?): List<Like>
}