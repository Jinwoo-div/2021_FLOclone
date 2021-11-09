package com.example.layout

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.layout.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val view = binding.root
        Handler(Looper.getMainLooper()).postDelayed({val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)// 2초간 딜레이 후 코드실행 -> 2초 뒤에 메인으로 이동(자동로그인등 처리 가능)

    }

}