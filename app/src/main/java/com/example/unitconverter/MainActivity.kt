package com.example.unitconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.unitconverter.Fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavBar = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        val areaFragment = SpeedFragment()
        val lengthFragment = LengthFragment()
        val massFragment = MassFragment()
        val temperatureFragment= TemperatureFragment()
        val timeFragment = TimeFragment()

        bottomNavBar.setOnItemSelectedListener {
            when(it.itemId){

                R.id.speed->setCurrentFragment(areaFragment)
                R.id.length->setCurrentFragment(lengthFragment)
                R.id.mass->setCurrentFragment(massFragment)
                R.id.temperature -> setCurrentFragment(temperatureFragment)
                R.id.time -> setCurrentFragment(timeFragment)

            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}