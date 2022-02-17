package com.example.FLO_clone.ui.signup

interface SignupView {
    fun onSignupLoading()
    fun onSignupSuccess()
    fun onSignupFailure(code: Int, message: String)
}