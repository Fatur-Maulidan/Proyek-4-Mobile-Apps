package Retrofit

import Model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiEndpoint {
    @POST("auth/login")
    fun postLogin(
       @Body mahasiswaAktif: MahasiswaAktif
    ): Call<ResponseMahasiswaAktif>

    @POST("auth/register")
    fun postRegister(
        @Body mahasiswaAktif: MahasiswaAktif
    ): Call<ResponseMessage>

    @GET("posts")
    fun getPosts(): Call<ArrayList<PostResponse>>

    @POST("auth/forgot-password")
    fun forgotPassword(@Body requestBody: ForgotPasswordRequest): Call<MessageResponse>
}