package com.example.mobileapplication

import CustomClass.CustomLayout
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class HomeActivity : AppCompatActivity() {
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
    }
}