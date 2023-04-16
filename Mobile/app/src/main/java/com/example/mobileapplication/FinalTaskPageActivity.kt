package com.example.mobileapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class FinalTaskPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final_task_page)

        val varTVTitle : TextView = findViewById(R.id.tvTitle)
        val varTVText : TextView = findViewById(R.id.tvText)

        val bundle: Bundle?= intent.extras
        varTVTitle.text = bundle?.getString("title")
        varTVText.text = bundle?.getString("text")

    }
}