package com.example.FLO_clone.ui.login

import com.example.FLO_clone.Auth

interface LoginView {
    fun onLoginLoading()
    fun onLoginSuccess(auth: Auth)
    fun onLoginFailure(code: Int, message: String)
}