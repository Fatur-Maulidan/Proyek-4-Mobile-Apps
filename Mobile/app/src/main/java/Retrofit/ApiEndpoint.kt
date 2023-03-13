package Retrofit

import Model.MahasiswaAktif
import Model.ResponseMahasiswaAktif
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST

interface ApiEndpoint {
    @POST("auth/login")
    fun postLogin(
       @Body mahasiswaAktif: MahasiswaAktif
    ): Call<ResponseMahasiswaAktif>

    @POST('auth/register')
    fun postRegister(

    )
}