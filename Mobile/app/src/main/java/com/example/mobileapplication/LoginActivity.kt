package com.example.mobileapplication

import CustomClass.*
import CustomInterface.ExitApps
import CustomInterface.onBackExitPressed
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.*


class LoginActivity : DispatchTouchEvent(), ExitApps {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Variabel objek untuk mengambil fungsi dari CustomLayout
        val customLayout = CustomLayout(applicationContext)

        val image: ImageView = findViewById(R.id.image)

//        customLayout.customBackground(image)

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

        customLayout.customBackground(image,windowManager)

//      Ketika button masuk diklik
        varBtnMasuk.setOnClickListener(View.OnClickListener {
            if(varEtNim.getText().toString() == "211511020" && varEtPassword.getText().toString() == "Admin") {
                customLayout.showCustomToast("Berhasil Login", R.layout.toast_custom_layout_success)
                startActivity(Intent(this, HomeActivity::class.java))
                varTvNimHandle.text = null
                varTvPasswordHandle.text = null
                finishAffinity()
            }
            else if(varEtNim.text.isEmpty() || varEtPassword.text.isEmpty()) {
                if (!varEtNim.text.isEmpty()) varTvNimHandle.text = null
                else varTvNimHandle.text = "Nim harus diisi"
                if (!varEtPassword.text.isEmpty()) varTvPasswordHandle.text = null
                else varTvPasswordHandle.text = "Kata sandi harus diisi"
                customLayout.showCustomToast("Kolom NIM dan Kata Sandi Wajib Diisi!", R.layout.toast_custom_layout_failed)
            }
            else{
                varTvPasswordHandle.text = null
                varTvNimHandle.text = null
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

//  Override dari fungsi onBackPressed diisi dengan fungsi yang ada pada interface
    override fun onBackPressed(){
        onBackExitPressed(this)
    }
}