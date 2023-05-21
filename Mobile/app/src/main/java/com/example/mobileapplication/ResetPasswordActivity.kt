package com.example.mobileapplication

import CustomClass.CustomLayout
import CustomClass.LoadingDialog
import Model.MessageResponse
import Model.ResetPasswordRequest
import Retrofit.ApiEndpoint
import Retrofit.ApiService
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mobileapplication.databinding.ActivityResetPasswordBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResetPasswordActivity : AppCompatActivity() {
    lateinit var customLayout: CustomLayout
    private lateinit var binding: ActivityResetPasswordBinding
    lateinit var email: String
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        customLayout = CustomLayout(applicationContext)
        loadingDialog = LoadingDialog(this)

        val intent = intent
        email = intent.getStringExtra("email").toString()
        binding.tvEmail.text = email

        binding.btnKonfirmasi.setOnClickListener { resetPassword(binding.etPassword.text.toString(),
            binding.etPasswordConfirmation.text.toString()) }
    }

    private fun resetPassword(password: String, confirmation: String) {
        loadingDialog.startLoadingDialog()
        val apiService = ApiService().endPoint().create(ApiEndpoint::class.java)
        val requestBody: ResetPasswordRequest = ResetPasswordRequest(email, password, confirmation)

        if (password.equals("") || confirmation.equals("")) {
            customLayout.showCustomToast("Lengkapi form!", R.layout.toast_custom_layout_failed)
        } else {
            apiService.resetPassword(requestBody).enqueue(object: Callback<MessageResponse> {
                override fun onResponse(
                    call: Call<MessageResponse>,
                    response: Response<MessageResponse>
                ) {
                    if (response.isSuccessful) {
                        loadingDialog.dismissDialog()
                        customLayout.showCustomToast(response.body()!!.message,
                            R.layout.toast_custom_layout_success)
                        Log.d("ResetPasswordSuccess", response.body()!!.message)
                        startActivity(Intent(applicationContext, LoginActivity::class.java))
                        finish()
                    }
                }

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    loadingDialog.dismissDialog()
                    customLayout.showCustomToast(t.localizedMessage,R.layout.toast_custom_layout_failed)
                    Log.d("ResetPasswordFailed", t.localizedMessage)
                }
            })
        }
    }
}