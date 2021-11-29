package com.example.layout

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.layout.databinding.ActivitySignupBinding
import java.net.PasswordAuthentication

class SignupActivity: AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding

    private var firstTab = 1
    private var confirmFirstTab = 1
    override fun onCreate (saveInstanceState: Bundle?) {
        super.onCreate(saveInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
    }
    fun check(): Boolean {
        if (binding.signupIdEt.text.isEmpty() || binding.signupAddressEt.text.isEmpty() ||
            binding.signupIdEt.text.toString() == "아이디(이메일)" || binding.signupAddressEt.text.toString() == "직접입력"
        ) {
            Toast.makeText(this, "이메일 입력이 되지 않았습니다", Toast.LENGTH_SHORT).show()
            binding.signupIdEt.requestFocus()
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(
                InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY
            )
            return false
        }

        else if (binding.signupPwEt.text.isEmpty() || binding.signupPwEt.text.toString() == "비밀번호") {
            Toast.makeText(this, "비밀번호 입력이 되지 않았습니다", Toast.LENGTH_SHORT).show()
            binding.signupPwEt.requestFocus()
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(
                InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY
            )
            return false
        }

        else if (binding.signupPwConfirmEt.text.isEmpty() || binding.signupPwConfirmEt.text.toString() == "비밀번호 확인") {
            Toast.makeText(this, "확인 비밀번호 입력이 되지 않았습니다", Toast.LENGTH_SHORT).show()
            binding.signupPwConfirmEt.requestFocus()
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(
                InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY
            )
            return false
        }

        else if (binding.signupPwConfirmEt.text.toString() != binding.signupPwEt.text.toString()) {
            Toast.makeText(this, "확인 비밀번호가 다릅니다", Toast.LENGTH_SHORT).show()
            binding.signupPwConfirmEt.requestFocus()
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(
                InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY
            )
            return false
        }

        else if (binding.signupNameEt.text.isEmpty() || binding.signupNameEt.text.toString() == "사용자 이름") {
            Toast.makeText(this, "사용자 입력이 되지 않았습니다", Toast.LENGTH_SHORT).show()
            binding.signupNameEt.requestFocus()
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(
                InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY
            )
            return false
        }
        return true
    }

    fun initListener() {
        val addr = binding.signupAddressEt

        binding.signupAddressIv.setOnClickListener() {
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

        binding.signupAddressEt.setOnFocusChangeListener(object: View.OnFocusChangeListener {
            override fun onFocusChange(p0: View?, p1: Boolean) {
                if (binding.signupAddressEt.text.toString() == "직접입력") {
                    binding.signupAddressEt.setText("")
                    addr.setTextColor(Color.parseColor("#ff000000"))
                }
            }
        })

        binding.signupIdEt.setOnFocusChangeListener(object: View.OnFocusChangeListener {
            override fun onFocusChange(p0: View?, p1: Boolean) {
                if (binding.signupIdEt.text.toString() == "아이디(이메일)") {
                    binding.signupIdEt.setText("")
                    binding.signupIdEt.setTextColor(Color.parseColor("#ff000000"))
                }
            }
        })

        binding.signupPwEt.setOnFocusChangeListener(object: View.OnFocusChangeListener {
            override fun onFocusChange(p0: View?, p1: Boolean) {
                if (binding.signupPwEt.text.toString() == "비밀번호") {
                    binding.signupPwEt.setText("")
                    binding.signupPwEt.setTextColor(Color.parseColor("#ff000000"))
                    if (firstTab == 1) {
                        binding.signupPwEt.inputType =
                            InputType.TYPE_CLASS_TEXT + InputType.TYPE_TEXT_VARIATION_PASSWORD
                        firstTab = 0
                    }
                }
            }

        })

        binding.signupPwConfirmEt.setOnFocusChangeListener(object: View.OnFocusChangeListener {
            override fun onFocusChange(p0: View?, p1: Boolean) {
                if (binding.signupPwConfirmEt.text.toString() == "비밀번호 확인") {
                    binding.signupPwConfirmEt.setText("")
                    binding.signupPwConfirmEt.setTextColor(Color.parseColor("#ff000000"))
                }
                if (confirmFirstTab == 1) {
                    binding.signupPwConfirmEt.inputType =
                        InputType.TYPE_CLASS_TEXT + InputType.TYPE_TEXT_VARIATION_PASSWORD
                    confirmFirstTab = 0
                }
            }

        })

        binding.signupPwIv.setOnClickListener() {
            if (binding.signupPwEt.inputType == InputType.TYPE_CLASS_TEXT) {
                if (binding.signupPwEt.text.toString() == "비밀번호") {
                }
                else {
                    binding.signupPwEt.inputType =
                        InputType.TYPE_CLASS_TEXT + InputType.TYPE_TEXT_VARIATION_PASSWORD
                    binding.signupPwIv.setImageResource(R.drawable.btn_input_password)
                }
            }
            else {
                binding.signupPwEt.inputType = InputType.TYPE_CLASS_TEXT
                binding.signupPwIv.setImageResource(R.drawable.btn_input_password_off)
            }
        }

        binding.signupPwConfirmIv.setOnClickListener() {
            if (binding.signupPwConfirmEt.inputType == InputType.TYPE_CLASS_TEXT) {
                if (binding.signupPwConfirmEt.text.toString() == "비밀번호 확인") {
                }
                else {
                    binding.signupPwConfirmEt.inputType =
                        InputType.TYPE_CLASS_TEXT + InputType.TYPE_TEXT_VARIATION_PASSWORD
                    binding.signupPwConfirmIv.setImageResource(R.drawable.btn_input_password)
                }
            }
            else {
                binding.signupPwConfirmEt.inputType = InputType.TYPE_CLASS_TEXT
                binding.signupPwConfirmIv.setImageResource(R.drawable.btn_input_password_off)
            }
        }

        binding.signupNameEt.setOnFocusChangeListener(object: View.OnFocusChangeListener {
            override fun onFocusChange(p0: View?, p1: Boolean) {
                if (binding.signupNameEt.text.toString() == "사용자 이름") {
                    binding.signupNameEt.setText("")
                    binding.signupNameEt.setTextColor(Color.parseColor("#ff000000"))
                }
            }

        })

        binding.signupFinTv.setOnClickListener() {
            var chk = check()
            if (chk) {
                var db = SongDatabase.getInstance(this)!!
                var id = binding.signupIdEt.text.toString() + "@" + binding.signupAddressEt.text.toString()
                var pw = binding.signupPwEt.text.toString()
                var name = binding.signupNameEt.text.toString()
                var user = db.UserDao().compareUser(id)
                if (user == null) {
                    user = User(id, pw, name)
                    db.UserDao().insert(user)
                    finish()
                }
                else {
                    Toast.makeText(this, "중복된 이메일입니다", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}