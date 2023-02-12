package com.example.mobileapplication

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

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

        fun isEtEmpty(editTextVar: EditText): Boolean {
            if (editTextVar.text.toString() == "") return true
            return false
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

        passwordToggle(varEtKataSandi, varImgViewShowPass1)
        passwordToggle(varEtKonfirmasiKataSandi, varImgViewShowPass2)

        varBtnDaftar.setOnClickListener(View.OnClickListener {
            varTvNama.text =
                if (varEtNama.text.toString().isEmpty()) "NAMA TIDAK BOLEH KOSONG" else null
            varTvNIM.text =
                if (varEtNim.text.toString().isEmpty()) "NIM TIDAK BOLEH KOSONG" else null
            varTvEmail.text =
                if (varEtEmail.text.toString().isEmpty()) "E-MAIL TIDAK BOLEH KOSONG" else null
            varTvKataSandi.text =
                if (varEtKataSandi.text.toString()
                        .isEmpty()
                ) "KATA SANDI TIDAK BOLEH KOSONG" else null
            varTvKonfirmasiKataSandi.text = if (varEtKonfirmasiKataSandi.text.toString()
                    .isEmpty()
            ) "MASUKAN KATA SANDI YANG SAMA" else null
        })
    }
}
