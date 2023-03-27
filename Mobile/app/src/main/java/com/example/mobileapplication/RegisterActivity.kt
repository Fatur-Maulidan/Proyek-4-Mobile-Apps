package com.example.mobileapplication

import CustomClass.CustomLayout
import CustomClass.DispatchTouchEvent
import Model.MahasiswaAktif
import Model.ResponseMahasiswaAktif
import Model.ResponseMessage
import Retrofit.ApiEndpoint
import Retrofit.ApiService
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import kotlin.math.log

class RegisterActivity : DispatchTouchEvent() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

//      Instansiasi Objek dari class CustomLayout
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

        // spinner Variabel
        var varSpJurusan: Spinner = findViewById(R.id.spinnerJurusan)
        var varSpProdi: Spinner = findViewById(R.id.spinnerProgramStudi)
        var varSpAngkatan: Spinner = findViewById(R.id.spinnerAngkatan)

        // button Variable
        val varBtnDaftar: Button = findViewById(R.id.buttonDaftar)

        // imageView Variable
        val varImgViewShowPass1: ImageView = findViewById(R.id.imageViewShowPassword1)
        val varImgViewShowPass2: ImageView = findViewById(R.id.imageViewShowPassword2)
        val varImgTopBackground: ImageView = findViewById(R.id.imageTopBackgroundRegister)

        // Custom Background Dinamic Ratio
        //--> Fungsi ini masih uji coba
        customLayout.resizeAndSetImage(varImgTopBackground,R.drawable.register_page_ellipse)
        //-->

        // Icon Toggle hide/unhide password
        customLayout.passwordToggle(varEtKataSandi, varImgViewShowPass1)
        customLayout.passwordToggle(varEtKonfirmasiKataSandi, varImgViewShowPass2)

        // menset data spinner
        setUpSpinner(varSpJurusan, varSpProdi, varSpAngkatan)

        // Ketika button Daftar di tekan
        varBtnDaftar.setOnClickListener(View.OnClickListener {
            var isEmailValid: (String, String) -> Boolean =
                { email: String, emailParameter: String -> email.contains(emailParameter) }

            // Handle untuk NIM
            if (varEtNim.text.isEmpty()) varTvNIM.text = "Nim tidak boleh kosong"
            else {
                if (varEtNim.text.length < 9) varTvNIM.text = "NIM harus berjumlah 9 digit"
                else varTvNIM.text = ""
            }

            // Handle untuk Nama
            if (varEtNama.text.isEmpty()) varTvNama.text = "Nama tidak boleh kosong"
            else {
                if (varEtNama.text.contains("^[ A-Za-z]+\$")) varTvNama.text = "Nama hanya boleh menggunakan alphabet dan spasi"
                else varTvNama.text = ""
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
                else -> varTvKonfirmasiKataSandi.text = ""}

            if(customLayout.isEditTextInputEmpty(varEtNama,varEtNim,varEtEmail,varEtKataSandi,varEtKonfirmasiKataSandi) == false) customLayout.showCustomToast("Form tidak boleh kosong", R.layout.toast_custom_layout_failed)
            else if (varTvNama.text.toString() == "" && varTvNIM.text.toString() == "" && varTvEmail.text.toString() == "" && varTvKataSandi.text.toString() == "" && varTvKonfirmasiKataSandi.text.toString() == "" && customLayout.isEditTextInputEmpty(varEtNama,varEtNim,varEtEmail,varEtKataSandi,varEtKonfirmasiKataSandi)){
                if (varEtKataSandi.text.toString() != varEtKonfirmasiKataSandi.text.toString()) varTvKonfirmasiKataSandi.text = "Kata Sandi tidak cocok"
                else registerAuth(registerForm(varEtNim.text.toString(),varEtNama.text.toString(),varSpJurusan.selectedItem.toString(),varSpProdi.selectedItem.toString(),varSpAngkatan.selectedItem.toString(),varEtEmail.text.toString(),varEtKataSandi.text.toString(), varEtKonfirmasiKataSandi.text.toString()))
            }
        })

        varSpJurusan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                if (selectedItem == "Bahasa Inggris") {
                    varSpProdi.isEnabled = false
                    varSpProdi.setSelection(1)
                } else {
                    varSpProdi.isEnabled = true
                    varSpProdi.setSelection(0)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
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

    private fun registerForm(vararg input: String): MahasiswaAktif {
        val mahasiswaAktif = MahasiswaAktif()

        var inputArray = Array<String>(8) {""}
        var count = 0
        for (inputs in input){
            inputArray[count] = inputs
            count += 1
        }
        mahasiswaAktif.nim = inputArray[0]
        mahasiswaAktif.nama = inputArray[1]
        mahasiswaAktif.jurusan = inputArray[2]
        mahasiswaAktif.program_studi = inputArray[3]
        mahasiswaAktif.angkatan = inputArray[4]
        mahasiswaAktif.email = inputArray[5]
        mahasiswaAktif.password = inputArray[6]
        mahasiswaAktif.password_confirmation = inputArray[7]

        return mahasiswaAktif
    }

    private fun registerAuth(mahasiswaAktif: MahasiswaAktif){
        val customLayout = CustomLayout(applicationContext)
        mahasiswaAktif.angkatan?.toString()?.let { Log.d("RegisterActivity", it) }
        val apiService = ApiService().endPoint().create(ApiEndpoint::class.java)
        apiService.postRegister(mahasiswaAktif).enqueue(object : Callback<ResponseMessage> {
            override fun onResponse(call: Call<ResponseMessage>, response: Response<ResponseMessage>) {
                var mahasiswaResponse = response.body()
                Log.d("RegisterActivity",mahasiswaResponse?.message.toString())
                if (mahasiswaResponse != null) {
                    customLayout.showCustomToast(
                        mahasiswaResponse?.message.toString(),
                        R.layout.toast_custom_layout_success
                    )
                    startActivity(
                        Intent(
                            this@RegisterActivity,
                            RegisterActivitySuccess::class.java
                        )
                    )
                    finishAffinity()
                } else {
                    customLayout.showCustomToast("Akun tidak Bisa terdaftar mohon cek kembali",R.layout.toast_custom_layout_failed)
                }
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                customLayout.showCustomToast(t.localizedMessage,R.layout.toast_custom_layout_failed)
            }
        })
    }
}
