package Retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {
    private val BASE_URL =
        "https://jsonplaceholder.typicode.com/"
//  "http://10.50.202.226:8000/api/" -> BASE_URL KAMPUS
//  "http://10.51.150.192:8000/api/" -> BASE_URL KAMPUS_2
//  "http://192.168.1.5:8000/api/" -> BASE_URL MAMAH
//  "https://jsonplaceholder.typicode.com/posts" -> BASE_URL TEST BODY

    fun endPoint(): Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
    }
}