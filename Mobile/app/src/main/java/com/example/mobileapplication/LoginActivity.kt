package com.example.mobileapplication

import CustomClass.*
import CustomInterface.ExitApps
import CustomInterface.onBackExitPressed
import HandlerCustom.FileHandler
import KeyStore.CryptoManager
import KeyStore.Preferences
import Model.*
import Retrofit.ApiEndpoint
import Retrofit.ApiService
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream


class LoginActivity : DispatchTouchEvent(), ExitApps {
    val preferences = Preferences()
    val cryptoManager = CryptoManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val customLayout = CustomLayout(applicationContext)
        val fileHandler = FileHandler()

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

//      Deklarasi Variabel imageView
        val varImgTopLogin: ImageView = findViewById(R.id.image)

//      Fungsi untuk resize imageView menjadi lebih dinamis diikuti dengan ratio yang ada
//--> Di Bagian ini masih test
        customLayout.resizeAndSetImage(varImgTopLogin, R.drawable.login_page_ellipse)
//-->

//      Ketika button masuk diklik
        varBtnMasuk.setOnClickListener(View.OnClickListener {
            if(!varEtNim.getText().toString().equals("") && !varEtPassword.getText().toString().equals("")) {
                loginAuth(loginForm(varEtNim.text.toString(),varEtPassword.text.toString()), fileHandler.checkFileIsExits(File(filesDir, "secret.txt")))
                customLayout.setTextViewNull(varTvNimHandle,varTvPasswordHandle)
            }
            else if(varEtNim.text.isEmpty() || varEtPassword.text.isEmpty()) {
                when {!varEtNim.text.isEmpty() -> {varTvNimHandle.text = null} else -> {varTvNimHandle.text = "Nim harus diisi"}}
                when {!varEtPassword.text.isEmpty() -> {varTvPasswordHandle.text = null} else -> {varTvPasswordHandle.text = "Kata sandi harus diisi"}}
                customLayout.showCustomToast("Kolom NIM dan Kata Sandi Wajib Diisi!", R.layout.toast_custom_layout_failed)
            }
            else{
                customLayout.setTextViewNull(varTvNimHandle,varTvPasswordHandle)
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

//  Form penyimpanan sementara dengan menggunkan data dari model
    private fun loginForm(nim: String, password: String): MahasiswaAktif{
        val mahasiswaAktif = MahasiswaAktif()
        mahasiswaAktif.nim = nim
        mahasiswaAktif.password = password

        return mahasiswaAktif
    }

//  Authentikasi untuk proses login
    private fun loginAuth(mahasiswaAktif: MahasiswaAktif, fos: FileOutputStream){
        val customLayout = CustomLayout(applicationContext)
        val apiService = ApiService().endPoint().create(ApiEndpoint::class.java)
        apiService.postLogin(mahasiswaAktif).enqueue(object :
            Callback<ResponseMahasiswaAktif> {
            override fun onResponse(call: Call<ResponseMahasiswaAktif>, response: Response<ResponseMahasiswaAktif>) {
                val mahasiswaResponse = response.body()
                if(mahasiswaResponse != null){
                    preferences.setToken(applicationContext, mahasiswaResponse?.token.toString())
                    mahasiswaResponse?.token?.encodeToByteArray()?.let {
                        cryptoManager.encrypt(
                            bytes = it,
                            outputStream = fos
                        ).decodeToString()
                    }
                    customLayout.showCustomToast(mahasiswaResponse?.message.toString(),R.layout.toast_custom_layout_success)
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    finishAffinity()
                } else {
                    customLayout.showCustomToast("Akun tidak terdaftar",R.layout.toast_custom_layout_failed)
                }
            }
            override fun onFailure(call: Call<ResponseMahasiswaAktif>, t: Throwable) {
                customLayout.showCustomToast(t.localizedMessage,R.layout.toast_custom_layout_failed)
            }
        })
    }
}