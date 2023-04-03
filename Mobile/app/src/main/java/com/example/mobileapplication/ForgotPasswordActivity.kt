package com.example.mobileapplication

import CustomClass.CustomLayout
import Model.ForgotPasswordRequest
import Model.MessageResponse
import Retrofit.ApiEndpoint
import Retrofit.ApiService
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.mobileapplication.databinding.ActivityForgotPasswordBinding
import kotlinx.android.synthetic.main.activity_forgot_password.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordActivity : AppCompatActivity() {
    lateinit var customLayout: CustomLayout
    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        customLayout = CustomLayout(applicationContext)

        binding.btnKirim.setOnClickListener { requestOTP(etEmail.text.toString()) }
    }

    private fun requestOTP(email: String) {
        val apiService = ApiService().endPoint().create(ApiEndpoint::class.java)
        val requestBody: ForgotPasswordRequest = ForgotPasswordRequest(email)

        apiService.forgotPassword(requestBody).enqueue(object: Callback<MessageResponse> {
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                if (response.isSuccessful) {
                    customLayout.showCustomToast(response.body()!!.message,
                        R.layout.toast_custom_layout_success)
                    Log.d("ForgotPasswordSuccess", response.body()!!.message)
                    startActivity(Intent(applicationContext, ForgotPasswordSuccessActivity::class.java))
                } else if (response.code() == 404) {
                    customLayout.showCustomToast("Akun tidak terdaftar.",
                        R.layout.toast_custom_layout_failed)
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                customLayout.showCustomToast(t.localizedMessage,R.layout.toast_custom_layout_failed)
                Log.d("ForgotPasswordFailed", t.localizedMessage)
            }
        })
    }
}