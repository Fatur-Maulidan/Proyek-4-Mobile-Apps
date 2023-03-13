package com.example.mobileapplication

import KeyStore.CryptoManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import KeyStore.Preferences
import java.io.File
import java.io.FileInputStream


class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val preferences = Preferences()
        val cryptoManager = CryptoManager()


        Handler(Looper.getMainLooper()).postDelayed({
            if(preferences.getToken(applicationContext).equals("")){
                startActivity(Intent(this, LoginActivity::class.java))
                finishAffinity()
            } else if (preferences.getToken(applicationContext)
                    .equals(cryptoManager.decrypt(
                        inputStream = FileInputStream(File(filesDir, "secret.txt"))
                    ).decodeToString())) {
                startActivity(Intent(this, HomeActivity::class.java))
                finishAffinity()
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finishAffinity()
            }
        }, 3000)
    }
}