package com.example.layout

import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthService {
    private lateinit var signupView: SignupView
    private lateinit var  loginView: LoginView

    fun setSignupView(signupView: SignupView) {
        this.signupView = signupView
    }

    fun setLoginView(loginView: LoginView) {
        this.loginView = loginView
    }

    fun signup(user: User) {
        val retrofit = Retrofit.Builder().baseUrl("http://13.125.121.202").addConverterFactory(GsonConverterFactory.create()).build()
        val authService = retrofit.create(AuthRetrofitInterface::class.java)

        signupView.onSignupLoading()

        authService.signup(user).enqueue(object:
            Callback<AuthResponse> {
            override fun onResponse(
                call: Call<AuthResponse>,
                response: Response<AuthResponse>//응답왔을때
            ) {
                Log.d("responseS", response.toString())
                val resp = response.body()!!
                Log.d("responseSS", resp.toString())

                when(resp.code) {
                    1000 -> signupView.onSignupSuccess()//성공
                    else -> signupView.onSignupFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {//네트워크 자체실패
                Log.d("failS", t.message.toString())
                signupView.onSignupFailure(400, "네트워크 오류")
            }
        })
    }

    fun login(user: User) {
        val retrofit = Retrofit.Builder().baseUrl("http://13.125.121.202").addConverterFactory(GsonConverterFactory.create()).build()
        val authService = retrofit.create(AuthRetrofitInterface::class.java)

        loginView.onLoginLoading()

        authService.login(user).enqueue(object: Callback<AuthResponse>{
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                val resp = response.body()!!
                Log.d("loginL", resp.toString())
                when(resp.code) {
                    1000->loginView.onLoginSuccess(resp.result!!)
                    else->loginView.onLoginFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                loginView.onLoginFailure(400, "네트워크 오류")
            }
        })
    }
}