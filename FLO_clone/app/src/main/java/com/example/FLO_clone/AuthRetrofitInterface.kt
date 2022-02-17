package com.example.FLO_clone

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/users")
    fun signup(@Body user: User): Call<AuthResponse>

    @POST("/users/login")
    fun login(@Body user: User): Call<AuthResponse>
}