package com.example.FLO_clone

import androidx.room.*

@Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM UserTable WHERE email = :userid")
    fun compareUser(userid: String): User?

    @Query("SELECT * FROM UserTable WHERE email = :id AND password = :pw")
    fun loginUser(id: String, pw: String): User?

    @Query("SELECT * FROM UserTable WHERE id = :id")
    fun getUser(id: Int?): User?

}