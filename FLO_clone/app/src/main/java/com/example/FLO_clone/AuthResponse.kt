package com.example.FLO_clone

import com.google.gson.annotations.SerializedName

data class Auth (
    val userIdx: Int,
    val jwt: String
)

data class AuthResponse (
    @SerializedName("isSuccess")val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: Auth?
)
