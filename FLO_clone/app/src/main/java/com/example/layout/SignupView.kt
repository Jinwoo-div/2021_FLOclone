package com.example.layout

interface SignupView {
    fun onSignupLoading()
    fun onSignupSuccess()
    fun onSignupFailure(code: Int, message: String)
}