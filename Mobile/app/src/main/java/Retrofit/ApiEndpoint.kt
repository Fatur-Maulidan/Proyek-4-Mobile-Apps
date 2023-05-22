package Retrofit

import Model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
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
    suspend fun getJurusan(@Header("Authorization") token: String): Response<List<String>>

    @GET("jurusan/{jurusan}/program-studi")
    fun getProgramStudi(@Header("Authorization") token: String, @Path("jurusan") jurusan: String): Call<ArrayList<ProgramStudi>>

    @GET("program-studi/{program-studi}/tugas-akhir")
    fun getTugasAkhir(@Header("Authorization") authToken: String, @Path("program-studi") program: String): Call<ArrayList<TugasAkhirResponse>>

    @GET("tugas-akhir/{id}")
    fun getTugasAkhirById(@Header("Authorization") authToken: String, @Path("id") id: String): Call<DetailTugasAkhir>

    @POST("auth/forgot-password")
    fun forgotPassword(@Body requestBody: ForgotPasswordRequest): Call<MessageResponse>

    @GET
    fun downloadPdfFile(@Url pdfUrl: String): Call<ResponseBody>
}