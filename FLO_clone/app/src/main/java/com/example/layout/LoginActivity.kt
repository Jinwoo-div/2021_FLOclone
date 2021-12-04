package com.example.layout

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Database
import com.example.layout.databinding.ActivityLoginBinding

class LoginActivity: AppCompatActivity(), LoginView {
    lateinit var binding : ActivityLoginBinding

    private var firstTab = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
    }

    fun check():Boolean {
        if (binding.loginIdEt.text.isEmpty() || binding.loginIdEt.text.toString() == "아이디(이메일)" ||
                    binding.loginAddressEt.text.isEmpty() || binding.loginAddressEt.text.toString() == "직접입력") {
            Toast.makeText(this, "이메일 입력이 되지 않았습니다", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (binding.loginPwEt.text.isEmpty() || binding.loginPwEt.text.toString() == "비밀번호") {
            Toast.makeText(this, "비밀번호 입력이 되지 않았습니다", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    fun initListener() {
        val addr = binding.loginAddressEt

        binding.loginAddressIv.setOnClickListener() {
            var popup = PopupMenu(this, it)

            menuInflater?.inflate(R.menu.popup_mail, popup.menu)
            popup.show()
            popup.setOnMenuItemClickListener() {
                addr.isEnabled = false
                when(it.itemId){
                    R.id.mail_daum -> {
                        addr.setText("daum.net")
                        addr.setTextColor(Color.parseColor("#ff000000"))
                        return@setOnMenuItemClickListener true
                    }
                    R.id.mail_gmail -> {
                        addr.setText("gmail.com")
                        addr.setTextColor(Color.parseColor("#ff000000"))
                        return@setOnMenuItemClickListener true
                    }
                    R.id.mail_naver -> {
                        addr.setText("naver.com")
                        addr.setTextColor(Color.parseColor("#ff000000"))
                        return@setOnMenuItemClickListener true
                    }
                    R.id.mail_inha -> {
                        addr.setText("inha.edu")
                        addr.setTextColor(Color.parseColor("#ff000000"))
                        return@setOnMenuItemClickListener true
                    }
                    R.id.mail_user -> {
                        addr.setText("직접입력")
                        addr.isEnabled = true
                        addr.setTextColor(Color.parseColor("#A6A6A6"))
                        return@setOnMenuItemClickListener true
                    }

                    else->
                        return@setOnMenuItemClickListener false
                }
            }
        }

        binding.loginAddressEt.setOnFocusChangeListener(object: View.OnFocusChangeListener {
            override fun onFocusChange(p0: View?, p1: Boolean) {
                if (binding.loginAddressEt.text.toString() == "직접입력") {
                    binding.loginAddressEt.setText("")
                    addr.setTextColor(Color.parseColor("#ff000000"))
                }
            }
        })

        binding.loginIdEt.setOnFocusChangeListener(object: View.OnFocusChangeListener {
            override fun onFocusChange(p0: View?, p1: Boolean) {
                if (binding.loginIdEt.text.toString() == "아이디(이메일)") {
                    binding.loginIdEt.setText("")
                    binding.loginIdEt.setTextColor(Color.parseColor("#ff000000"))
                }
            }
        })

        binding.loginPwEt.setOnFocusChangeListener(object: View.OnFocusChangeListener {
            override fun onFocusChange(p0: View?, p1: Boolean) {
                if (binding.loginPwEt.text.toString() == "비밀번호") {
                    binding.loginPwEt.setText("")
                    binding.loginPwEt.setTextColor(Color.parseColor("#ff000000"))
                    if (firstTab == 1) {
                        binding.loginPwEt.inputType =
                            InputType.TYPE_CLASS_TEXT + InputType.TYPE_TEXT_VARIATION_PASSWORD
                        firstTab = 0
                    }
                }
            }

        })

        binding.loginPwIv.setOnClickListener() {
            if (binding.loginPwEt.inputType == InputType.TYPE_CLASS_TEXT) {
                if (binding.loginPwEt.text.toString() == "비밀번호") {
                }
                else {
                    binding.loginPwEt.inputType =
                        InputType.TYPE_CLASS_TEXT + InputType.TYPE_TEXT_VARIATION_PASSWORD
                    binding.loginPwIv.setImageResource(R.drawable.btn_input_password)
                }
            }
            else {
                binding.loginPwEt.inputType = InputType.TYPE_CLASS_TEXT
                binding.loginPwIv.setImageResource(R.drawable.btn_input_password_off)
            }
        }

        binding.loginCloseIv.setOnClickListener() {
            finish()
        }

        binding.loginGoSignupTv.setOnClickListener() {
            var intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        binding.loginGoLoginTv.setOnClickListener() {
            val chk = check()
            if (chk) {
                var id = binding.loginIdEt.text.toString() + "@" + binding.loginAddressEt.text.toString()
                var pw = binding.loginPwEt.text.toString()
                val authService = AuthService()
                authService.setLoginView(this)
                authService.login(User(id, pw, ""))
            }
        }
    }

    override fun onLoginLoading() {
        binding.loginLoadingPb.visibility = View.VISIBLE

    }

    override fun onLoginSuccess(auth: Auth) {
        binding.loginLoadingPb.visibility = View.GONE
        val spf = getSharedPreferences("auth", MODE_PRIVATE)
        val editor = spf.edit()
        editor.putString("jwt", auth.jwt)
        editor.putInt("userIdx", auth.userIdx)
        editor.apply()

        finish()
    }

    override fun onLoginFailure(code: Int, message: String) {
        binding.loginLoadingPb.visibility = View.GONE
        Toast.makeText(this, "일치하는 회원정보가 없습니다", Toast.LENGTH_LONG).show()
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}