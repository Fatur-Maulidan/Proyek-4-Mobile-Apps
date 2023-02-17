package com.example.mobileapplication

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle

import android.view.View
import android.widget.*


class LoginActivity : DispatchTouchEvent() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val customLayout = CustomLayout(applicationContext)

//      Deklarasi Variabel dari Layout
        val varEtNim: EditText = findViewById(R.id.editTextNIM)
        val varEtPassword: EditText = findViewById(R.id.editTextKataSandi)
        val varImgViewShowPass: ImageView = findViewById(R.id.imageViewShowPassword)

//      Variabel yang bisa di klik
        val varBtnMasuk: Button = findViewById(R.id.buttonMasuk)
        val varTvRegister: TextView = findViewById(R.id.textViewDaftar)
        val varTvLupaPassword: TextView = findViewById(R.id.textViewLupaPassword)

//      Variabel untuk handle berupa message
        val varTvNimHandle: TextView = findViewById(R.id.textViewNimHandle)
        val varTvPasswordHandle: TextView = findViewById(R.id.textViewKataSandiHandle)

//      Pemanggilan untuk hide/unhide password
        customLayout.passwordToggle(varEtPassword,varImgViewShowPass)

//      Ketika button masuk diklik
        varBtnMasuk.setOnClickListener(View.OnClickListener {
            if(varEtNim.getText().toString() == "211511020" && varEtPassword.getText().toString() == "Admin") {
                customLayout.showCustomToast("Berhasil Login", R.layout.toast_custom_layout_success)
                startActivity(Intent(this, ForgotPasswordActivity::class.java))
                varTvNimHandle.setText("")
                varTvPasswordHandle.setText("")
            }
            else if(varEtNim.getText().toString() == "" || varEtPassword.getText().toString() == "") {
                if (varEtNim.getText().toString() != "") varTvNimHandle.setText("")
                else varTvNimHandle.setText("Nim harus diisi")
                if (varEtPassword.getText().toString() != "") varTvPasswordHandle.setText("")
                else varTvPasswordHandle.setText("Kata sandi harus diisi")
                customLayout.showCustomToast("Kolom NIM dan Kata Sandi Wajib Diisi!", R.layout.toast_custom_layout_failed)
            }
            else if(varEtNim.getText().toString() != "Admin" || varEtPassword.getText().toString() != "Admin"){
                varTvPasswordHandle.setText("")
                varTvNimHandle.setText("")
                customLayout.showCustomToast("Akun tidak terdaftar",R.layout.toast_custom_layout_failed)
            }
        })

//      Ketika text daftar diklik
        varTvRegister.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        })

//      Ketika text LupaPassword diklik
        varTvLupaPassword.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        })
    }

    override fun onBackPressed() {
        var custom = CustomLayout(applicationContext)
        custom.showAlertDialog()
    }
}