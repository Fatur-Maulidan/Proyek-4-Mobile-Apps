package com.example.mobileapplication

import CustomClass.DispatchTouchEvent
import CustomInterface.RecyclerViewInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.mobileapplication.databinding.ActivityHomeBinding

class HomeActivity : DispatchTouchEvent() {

    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())

        binding.bottomNavBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeBotNavBar -> replaceFragment(Home())
                R.id.peminjamanBotNavBar -> replaceFragment(Lend())

                else -> {

                }
            }
            true
        }
    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}