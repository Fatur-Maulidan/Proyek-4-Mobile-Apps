package Retrofit

import Model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiEndpoint {
    @POST("auth/login")
    fun postLogin(
       @Body mahasiswaAktif: MahasiswaAktif
    ): Call<ResponseMahasiswaAktif>

    @POST("auth/register")
    fun postRegister(
        @Body mahasiswaAktif: MahasiswaAktif
    ): Call<ResponseMessage>

    @GET("jurusan")
    fun getJurusan(@Header("Authorization") authToken: String): Call<JurusanResponse>

    @GET("tugas-akhir")
    fun getTugasAkhir(@Header("Authorization") authToken: String): Call<ArrayList<TugasAkhir>>

    @GET("tugas-akhir/{id}")
    fun getTugasAkhirById(@Header("Authorization") authToken: String, @Path("id") id: Int): Call<ArrayList<TugasAkhir>>

    @POST("auth/forgot-password")
    fun forgotPassword(@Body requestBody: ForgotPasswordRequest): Call<MessageResponse>

    @POST("auth/verify-otp")
    fun verifyOtp(@Body requestBody: VerifyOtpRequest): Call<VerifyOtpResponse>

    @POST("auth/reset-password")
    fun resetPassword(@Body requestBody: ResetPasswordRequest): Call<MessageResponse>
}