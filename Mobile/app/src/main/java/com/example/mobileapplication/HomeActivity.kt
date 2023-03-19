package com.example.mobileapplication

import CustomClass.CustomLayout
import CustomClass.DispatchTouchEvent
import CustomClass.PostAdapter
import Model.PostResponse
import Retrofit.ApiEndpoint
import Retrofit.ApiService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : DispatchTouchEvent() {

    private val list = ArrayList<PostResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

//      Instansiasi Objek dari class CustomLayout
        val customLayout = CustomLayout(applicationContext)

//      Variabel untuk deklarasikan image layout menjadi image variabel
        val image: ImageView = findViewById(R.id.imageTopBackgroundHome)

//-->   Fungsi ini masih uji coba
        customLayout.resizeAndSetImage(image,R.drawable.home_page_ellipse)
//-->

        contentView.setHasFixedSize(true)
        contentView.layoutManager = LinearLayoutManager(this)

        val apiService = ApiService().endPoint().create(ApiEndpoint::class.java)
        apiService.getPosts().enqueue(object : Callback<ArrayList<PostResponse>> {
            override fun onResponse(
                call: Call<ArrayList<PostResponse>>,
                response: Response<ArrayList<PostResponse>>
            ) {
                val responseCode: String = response.code().toString()
                response.body()?.let { list.addAll(it) }
                val adapter = PostAdapter(list)
                contentView.adapter = adapter
            }

            override fun onFailure(call: Call<ArrayList<PostResponse>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}