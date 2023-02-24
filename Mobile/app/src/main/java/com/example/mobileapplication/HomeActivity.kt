package com.example.mobileapplication

import CustomClass.CustomLayout
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val customLayout = CustomLayout(applicationContext)

        val image: ImageView = findViewById(R.id.imageTopBackgroundHome)
        customLayout.resizeAndSetImage(image,R.drawable.home_page_ellipse)
    }
}