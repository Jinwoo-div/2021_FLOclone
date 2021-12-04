package com.example.layout

interface LoginView {
    fun onLoginLoading()
    fun onLoginSuccess(auth: Auth)
    fun onLoginFailure(code: Int, message: String)
}