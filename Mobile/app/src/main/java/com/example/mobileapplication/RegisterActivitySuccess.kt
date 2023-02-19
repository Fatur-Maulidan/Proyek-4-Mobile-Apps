package com.example.mobileapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class RegisterActivitySuccess : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_success)

        val varBtnMasuk: Button = findViewById(R.id.buttonMasuk)

        varBtnMasuk.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        })
    }
}