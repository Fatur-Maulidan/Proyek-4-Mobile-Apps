package com.example.mobileapplication

import CustomClass.CustomLayout
import CustomClass.LoadingDialog
import Model.VerifyOtpRequest
import Model.VerifyOtpResponse
import Retrofit.ApiEndpoint
import Retrofit.ApiService
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mobileapplication.databinding.ActivityOtpBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtpActivity : AppCompatActivity() {
    lateinit var customLayout: CustomLayout
    private lateinit var binding: ActivityOtpBinding
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        customLayout = CustomLayout(applicationContext)
        loadingDialog = LoadingDialog(this)

        binding.btnKonfirmasi.setOnClickListener {
            val otp = binding.etOtp1.text.toString() + binding.etOtp2.text.toString() +
                    binding.etOtp3.text.toString() + binding.etOtp4.text.toString()
            verifyOTP(otp)
        }
    }

    private fun verifyOTP(otp: String) {
        loadingDialog.startLoadingDialog()
        val apiService = ApiService().endPoint().create(ApiEndpoint::class.java)
        val requestBody: VerifyOtpRequest = VerifyOtpRequest(otp)

        apiService.verifyOtp(requestBody).enqueue(object: Callback<VerifyOtpResponse> {
            override fun onResponse(
                call: Call<VerifyOtpResponse>,
                response: Response<VerifyOtpResponse>
            ) {
                if (response.isSuccessful) {
                    loadingDialog.dismissDialog()
                    customLayout.showCustomToast(response.body()!!.message,
                        R.layout.toast_custom_layout_success)
                    Log.d("OTPSuccess", response.body()!!.message)
                    val intent = Intent(this@OtpActivity, ResetPasswordActivity::class.java)
                    intent.putExtra("email", response.body()!!.email)
                    startActivity(intent)
                    finish()
                } else if (response.code() == 404) {
                    loadingDialog.dismissDialog()
                    customLayout.showCustomToast("Kode OTP tidak valid.",
                        R.layout.toast_custom_layout_failed)
                }
            }

            override fun onFailure(call: Call<VerifyOtpResponse>, t: Throwable) {
                loadingDialog.dismissDialog()
                customLayout.showCustomToast(t.localizedMessage,R.layout.toast_custom_layout_failed)
                Log.d("OTPFailed", t.localizedMessage)
            }
        })
    }
}