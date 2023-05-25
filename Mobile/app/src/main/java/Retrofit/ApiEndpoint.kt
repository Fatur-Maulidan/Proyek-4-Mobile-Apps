package Retrofit

import Model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiEndpoint {
//  Fungsi yang digunakan untuk proses authentikasi
    @POST("auth/login")
    fun postLogin(
       @Body mahasiswaAktif: MahasiswaAktif
    ): Call<ResponseMahasiswaAktif>

//  Fungsi ini digunakan untuk melakukan registrasi pada aplikasi repositori TA POLBAN
    @POST("auth/register")
    fun postRegister(
        @Body mahasiswaAktif: MahasiswaAktif
    ): Call<ResponseMessage>

//  Fungsi ini digunakan untuk mengambil semua data jurusan
    @GET("jurusan")
    suspend fun getJurusan(
        @Header("Authorization") token: String
    ): Response<List<String>>

//  Fungsi ini digunakan untuk mengambil semua data program studi
    @GET("jurusan/{jurusan}/program-studi")
    fun getProgramStudi(
        @Header("Authorization") token: String,
        @Path("jurusan") jurusan: String
    ): Call<ArrayList<ProgramStudi>>

//  Fungsi ini digunakan untuk mengambil semua data Tugas akhir dengan filter program studi tersebut dari database
    @GET("program-studi/{program-studi}/tugas-akhir")
    fun getTugasAkhir(
        @Header("Authorization") authToken: String,
        @Path("program-studi") program: String
    ): Call<ArrayList<TugasAkhirResponse>>

//  Fungsi ini digunakan untuk mengambil data Tugas Akhir yang dipilih oleh mahasiswa
    @GET("tugas-akhir/{id}")
    fun getTugasAkhirById(
        @Header("Authorization") authToken: String,
        @Path("id") id: String
    ): Call<DetailTugasAkhir>

//  Fungsi ini digunakan untuk send ke email untuk melakukan lupa password
    @POST("auth/forgot-password")
    fun forgotPassword(
        @Body requestBody: ForgotPasswordRequest
    ): Call<MessageResponse>

//  Fungsi ini digunakan untuk komparasi otp yang telah diinput dengan otp yang ada pada send kedalam email pengguna
    @POST("auth/verify-otp")
    fun verifyOtp(
        @Body requestBody: VerifyOtpRequest
    ): Call<VerifyOtpResponse>

//  Fungsi ini digunakan setelah user memberikan input
    @POST("auth/reset-password")
    fun resetPassword(
        @Body requestBody: ResetPasswordRequest
    ): Call<MessageResponse>

//  Fungsi ini digunakan untuk mendowload file PDF dari URL yang diberikan
    @GET
    fun downloadPdfFile(@Url pdfUrl: String): Call<ResponseBody>
}