package com.example.layout

import androidx.room.*

@Dao
interface AlbumDao {
    @Insert
    fun insert(albums: Albums)

    @Update
    fun update(albums: Albums)

    @Delete
    fun delete(albums: Albums)

    @Query("SELECT * FROM AlbumTable")
    fun getAlbums(): List<Albums>

    @Query("SELECT * FROM AlbumTable WHERE id = :id")
    fun getAlbumsById(id: Int?): Albums

    @Query("SELECT * FROM AlbumTable WHERE title = :title")
    fun getAlbumsByTitle(title: String?): Albums
}