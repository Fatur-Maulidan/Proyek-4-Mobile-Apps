package com.example.mobileapplication

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater

import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//      Declare Variable from layout
        val varEtNim: EditText = findViewById(R.id.editTextNIM)
        val varTvNimHandle: TextView = findViewById(R.id.textViewNimHandle)
        val varEtPassword: EditText = findViewById(R.id.editTextKataSandi)
        val varTvPasswordHandle: TextView = findViewById(R.id.textViewKataSandiHandle)
        val varBtnMasuk: Button = findViewById(R.id.buttonMasuk)
        val varImgViewShowPass: ImageView = findViewById(R.id.imageViewShowPassword)
        val varTvRegister: TextView = findViewById(R.id.textViewDaftar)
        val varTvLupaPassword: TextView = findViewById(R.id.textViewLupaPassword)

        passwordToggle(varEtPassword,varImgViewShowPass)

        varBtnMasuk.setOnClickListener(View.OnClickListener {
            if(varEtNim.getText().toString() == "Admin" && varEtPassword.getText().toString() == "Admin") {
                showCustomToast("Berhasil Login", R.layout.toast_custom_layout_success)
                varTvNimHandle.setText("")
                varTvPasswordHandle.setText("")
            }
            else if(varEtNim.getText().toString() == "" || varEtPassword.getText().toString() == "") {
                if (varEtNim.getText().toString() != "") varTvNimHandle.setText("")
                else varTvNimHandle.setText("Nim harus diisi")
                if (varEtPassword.getText().toString() != "") varTvPasswordHandle.setText("")
                else varTvPasswordHandle.setText("Kata sandi harus diisi")
                showCustomToast("Kolom NIM dan Kata Sandi Wajib Diisi!", R.layout.toast_custom_layout_failed
                )
            }
            else if(varEtNim.getText().toString() != "Admin" || varEtPassword.getText().toString() != "Admin"){
                    varTvPasswordHandle.setText("")
                    varTvNimHandle.setText("")
                    showCustomToast("Akun tidak terdaftar",R.layout.toast_custom_layout_failed)
            }

        })

        varTvRegister.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        })

        varTvLupaPassword.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
            finish()
        })
    }

    fun passwordToggle(editText: EditText, imageView: ImageView) {
        imageView.setOnClickListener {
            if (editText.transformationMethod == null) {
                editText.transformationMethod = PasswordTransformationMethod.getInstance()
                imageView.setImageResource(R.drawable.ic_baseline_visibility_off_24)
            } else {
                editText.transformationMethod = null
                imageView.setImageResource(R.drawable.ic_baseline_visibility_24)
            }
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v: View? = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm: InputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    public fun showCustomToast(textInput: String, toast_custom: Int) {
        val inflater: LayoutInflater = layoutInflater
        val layout: View = inflater.inflate(toast_custom, findViewById(R.id.toast_failed))
        val text: TextView = layout.findViewById(R.id.text)
        text.text = textInput
        val toast = Toast(applicationContext)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
    }
}