package com.codemobile.hackcatonapp.activity

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.codemobile.hackcatonapp.R
import com.codemobile.hackcatonapp.fragment.HomeFragment
import com.codemobile.hackcatonapp.fragment.LendingFragment
import com.codemobile.hackcatonapp.fragment.LoanFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var leandingFragment: LendingFragment
    lateinit var loanFragment: LoanFragment

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        return@OnNavigationItemSelectedListener when (item.itemId) {
            R.id.menu_home, R.id.menu_loan, R.id.menu_lean -> {
                changeFragment(item.itemId)
                true
            }

            else -> false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        leandingFragment = LendingFragment()
        loanFragment = LoanFragment()
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        checkUserPayment()
        val fragment = supportFragmentManager.findFragmentById(R.id.container)
        if (fragment == null) {
            changeFragment(navigation.selectedItemId)
        }
    }

    private fun checkUserPayment() {
        val x: Boolean = false
        if (x) {
            AlertDialog.Builder(this).setTitle("Payment").setMessage("DueDate Payment have arrive")
                .setPositiveButton("Pay", DialogInterface.OnClickListener { dialogInterface, i -> }).create()
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
                loanFragment
            }

            R.id.menu_lean -> {
                if (currentFragment is LendingFragment) return
                leandingFragment
            }

            else -> return
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, newFragment)
            .commit()
    }
}
