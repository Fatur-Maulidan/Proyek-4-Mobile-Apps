package com.example.mobileapplication

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : DispatchTouchEvent() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val customLayout = CustomLayout(applicationContext)

        // editText Variables
        val varEtNama: EditText = findViewById(R.id.editTextNama)
        val varEtNim: EditText = findViewById(R.id.editTextNIM)
        val varEtEmail: EditText = findViewById(R.id.editTextEmail)
        val varEtKataSandi: EditText = findViewById(R.id.editTextKataSandi)
        val varEtKonfirmasiKataSandi: EditText = findViewById(R.id.editTextKonfirmasiKataSandi)

        // textView Variables
        val varTvNama: TextView = findViewById(R.id.textViewNamaHandle)
        val varTvNIM: TextView = findViewById(R.id.textViewNIMHandle)
        val varTvEmail: TextView = findViewById(R.id.textViewEmailHandle)
        val varTvKataSandi: TextView = findViewById(R.id.textViewKataSandiHandle)
        val varTvKonfirmasiKataSandi: TextView =
            findViewById(R.id.textViewKonfirmasiKataSandiHandle)

        // button Variable
        val varBtnDaftar: Button = findViewById(R.id.buttonDaftar)

        // imageView Variable
        val varImgViewShowPass1: ImageView = findViewById(R.id.imageViewShowPassword1)
        val varImgViewShowPass2: ImageView = findViewById(R.id.imageViewShowPassword2)

        customLayout.passwordToggle(varEtKataSandi, varImgViewShowPass1)
        customLayout.passwordToggle(varEtKonfirmasiKataSandi, varImgViewShowPass2)

        varBtnDaftar.setOnClickListener(View.OnClickListener {
            if(!customLayout.isEditTextEmpty(varEtNama,varEtNim,varEtEmail,varEtKataSandi,varEtKonfirmasiKataSandi)){
                startActivity(Intent(this, LoginActivity::class.java))
            }

            if (varEtNama.text.isEmpty()) varTvNama.text = "NAMA TIDAK BOLEH KOSONG"
            else {
                if (varEtNama.text.contains("^[ A-Za-z]+\$")) varTvNama.text = "NAMA HANYA BOLEH MENGGUNAKAN ALFABET DAN SPASI"
                else varTvNama.text = null
            }

            // Handle untuk NIM
            if (varEtNim.text.toString().isEmpty()) varTvNIM.text = "NIM TIDAK BOLEH KOSONG"
            else {
                if (varEtNim.text.length < 9) varTvNIM.text = "NIM HARUS BERJUMLAH 9 DIGIT"
                else varTvNIM.text = null
            }

            // Handle untuk e-mail
            if (varEtEmail.text.isEmpty()) varTvEmail.text = "E-MAIL TIDAK BOLEH KOSONG"
            else {
                var isEmailValid: (String, String) -> Boolean =
                    { email: String, emailParameter: String -> email.contains(emailParameter) }
                if (!isEmailValid(varEtEmail.text.toString(), "polban.ac.id")) varTvEmail.text = "WAJIB MENGGUNAKAN E-MAIL POLBAN"
                else varTvEmail.text = null
            }

            // Handle untuk kata sandi
            varTvKataSandi.text = if (varEtKataSandi.text.toString().isEmpty()) "KATA SANDI TIDAK BOLEH KOSONG" else null

            // Handle untuk konfirmasi kata sandi
            when {varEtKonfirmasiKataSandi.text.toString().isEmpty() -> {
                    varTvKonfirmasiKataSandi.text = "MASUKAN KONFIRMASI KATA SANDI"
                }
                varEtKataSandi.text.toString() != varEtKonfirmasiKataSandi.text.toString() -> {
                    varTvKonfirmasiKataSandi.text = "KATA SANDI TIDAK COCOK"
                }
                else -> {
                    varTvKonfirmasiKataSandi.text = null
                }
            }
        })
    }
}
