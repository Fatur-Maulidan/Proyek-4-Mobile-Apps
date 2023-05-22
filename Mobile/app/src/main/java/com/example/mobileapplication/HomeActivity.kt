package com.example.mobileapplication

import CustomClass.DispatchTouchEvent
import CustomInterface.RecyclerViewInterface
import KeyStore.Preferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.mobileapplication.databinding.ActivityHomeBinding

class HomeActivity : DispatchTouchEvent() {

    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var preferences = Preferences()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(preferences?.getJurusan(applicationContext) != null && preferences?.getProdi(applicationContext) != null){
            replaceFragment(Home())
        } else if (preferences?.getJurusan(applicationContext) == null){
            replaceFragment(HomeFragmentFilterJurusan())
        } else if (preferences?.getJurusan(applicationContext) != null && preferences?.getJurusan(applicationContext) == null){
            replaceFragment(HomeFragmentFilterProdi())
        } else {
            replaceFragment(HomeFragmentFilterJurusan())
        }


        binding.bottomNavBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeBotNavBar ->
                    if(preferences?.getJurusan(applicationContext) != null && preferences?.getProdi(applicationContext) != null){
                        replaceFragment(Home())
                    } else if (preferences?.getJurusan(applicationContext) == null){
                        replaceFragment(HomeFragmentFilterJurusan())
                    } else if (preferences?.getJurusan(applicationContext) != null && preferences?.getProdi(applicationContext) == null){
                        replaceFragment(HomeFragmentFilterProdi())
                    } else {
                        replaceFragment(HomeFragmentFilterJurusan())
                    }
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