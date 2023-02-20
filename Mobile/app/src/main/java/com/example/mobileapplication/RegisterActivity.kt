package com.example.mobileapplication

import CustomClass.CustomLayout
import CustomClass.DispatchTouchEvent
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*

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

        // spinner
        val varSpJurusan: Spinner = findViewById(R.id.spinnerJurusan)
        val varSpProdi: Spinner = findViewById(R.id.spinnerProgramStudi)
        val varSpAngkatan: Spinner = findViewById(R.id.spinnerAngkatan)

        // button Variable
        val varBtnDaftar: Button = findViewById(R.id.buttonDaftar)

        // imageView Variable
        val varImgViewShowPass1: ImageView = findViewById(R.id.imageViewShowPassword1)
        val varImgViewShowPass2: ImageView = findViewById(R.id.imageViewShowPassword2)

        // Icon Toggle hide/unhide password
        customLayout.passwordToggle(varEtKataSandi, varImgViewShowPass1)
        customLayout.passwordToggle(varEtKonfirmasiKataSandi, varImgViewShowPass2)

        // menset data spinner
        setUpSpinner(varSpJurusan, varSpProdi, varSpAngkatan)

        // Ketika button Daftar di tekan
        varBtnDaftar.setOnClickListener(View.OnClickListener {
            var isEmailValid: (String, String) -> Boolean =
                { email: String, emailParameter: String -> email.contains(emailParameter) }

            if (varEtNama.text.isEmpty()) varTvNama.text = "Nama tidak boleh kosong"
            else {
                if (varEtNama.text.contains("^[ A-Za-z]+\$")) varTvNama.text = "Nama hanya boleh menggunakan alphabet dan spasi"
                else varTvNama.text = ""
            }

            // Handle untuk NIM
            if (varEtNim.text.isEmpty()) varTvNIM.text = "Nim tidak boleh kosong"
            else {
                if (varEtNim.text.length < 9) varTvNIM.text = "NIM harus berjumlah 9 digit"
                else varTvNIM.text = ""
            }

            // Handle untuk e-mail
            if (varEtEmail.text.isEmpty()) varTvEmail.text = "E-Mail tidak boleh kosong"
            else {
                if (!isEmailValid(varEtEmail.text.toString(), "polban.ac.id")) varTvEmail.text = "Wajib Menggunakan E-Mail POLBAN"
                else varTvEmail.text = ""
            }

            // Handle untuk kata sandi
            if (varEtKataSandi.text.isEmpty()) varTvKataSandi.text = "Kata sandi tidak boleh kosong" else varTvKataSandi.text = ""

            // Handle untuk konfirmasi kata sandi
            when {varEtKonfirmasiKataSandi.text.isEmpty() -> {varTvKonfirmasiKataSandi.text = "Masukan konfirmasi Kata Sandi"}
                varEtKataSandi.text.toString() != varEtKonfirmasiKataSandi.text.toString() -> {varTvKonfirmasiKataSandi.text = "Kata Sandi tidak cocok"}
                else -> varTvKonfirmasiKataSandi.text = ""}

            if(customLayout.isEditTextInputEmpty(varEtNama,varEtNim,varEtEmail,varEtKataSandi,varEtKonfirmasiKataSandi) == false) customLayout.showCustomToast("Form tidak boleh kosong", R.layout.toast_custom_layout_failed)
            else if (varTvNama.text.toString() == "" && varTvNIM.text.toString() == "" && varTvEmail.text.toString() == "" && varTvKataSandi.text.toString() == "" && varTvKonfirmasiKataSandi.text.toString() == "" && customLayout.isEditTextInputEmpty(varEtNama,varEtNim,varEtEmail,varEtKataSandi,varEtKonfirmasiKataSandi)){
                customLayout.showCustomToast("Registrasi Berhasil", R.layout.toast_custom_layout_success)
                startActivity(Intent(this, RegisterActivitySuccess::class.java))
                finishAffinity()
            }
        })
    }

    private fun setUpSpinner(spJurusan: Spinner, spProdi: Spinner, spAngkatan: Spinner) {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.jurusan,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spJurusan.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.program_studi,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spProdi.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.angkatan,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spAngkatan.adapter = adapter
        }
    }
}
