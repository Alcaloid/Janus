package com.codemobile.hackcatonapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        return@OnNavigationItemSelectedListener when (item.itemId) {
            R.id.menu_home, R.id.menu_loan -> {
                changeFragment(item.itemId)
                true
            }

            else -> false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        val fragment = supportFragmentManager.findFragmentById(R.id.container)
        if (fragment == null) {
            changeFragment(navigation.selectedItemId)
        }
    }

    private fun changeFragment(id: Int) {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.container)

        val newFragment = when (id) {
            R.id.menu_home -> {
                if (currentFragment is HomeFragment) return
                HomeFragment()
            }

            R.id.menu_loan -> {
                if (currentFragment is LoanFragment) return
                LoanFragment()
            }

            else -> return
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, newFragment)
            .commit()
    }
}
