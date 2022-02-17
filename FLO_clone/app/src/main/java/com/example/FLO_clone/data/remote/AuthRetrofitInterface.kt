package com.example.FLO_clone.data.remote

import com.example.FLO_clone.data.entities.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/users")
    fun signup(@Body user: User): Call<AuthResponse>

    @POST("/users/login")
    fun login(@Body user: User): Call<AuthResponse>
}