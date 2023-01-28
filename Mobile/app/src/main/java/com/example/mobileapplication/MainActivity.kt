package com.example.mobileapplication

import android.content.Context
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText

import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val varEtNim = findViewById<EditText>(R.id.editTextNIM)
        val varEtPassword = findViewById<EditText>(R.id.editTextKataSandi)
        val varBtnMasuk = findViewById<Button>(R.id.buttonMasuk)

        varBtnMasuk.setOnClickListener(View.OnClickListener {
            if(varEtNim.getText().toString() == "Admin" && varEtPassword.getText().toString() == "Admin")
                Toast.makeText(this, "Berhasil Admin",Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "Gagal Admin",Toast.LENGTH_SHORT).show()
        })
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v: View? = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm: InputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
}