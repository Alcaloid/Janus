package com.codemobile.hackcatonapp.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.codemobile.hackcatonapp.DEEPLINK
import android.content.ActivityNotFoundException
import android.widget.Toast


class PaymentActivity : AppCompatActivity() {
    private var deeplink: String = "scbeasysim://billpayment-anonymous/54667370-ca7b-4a5f-95e7-7d0e20db0848"
    private var callbackURL: String = "?callback_url=https://easy-loan.com/loan"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deeplink = intent.getStringExtra(DEEPLINK) as String
        val uri = Uri.parse(deeplink+callbackURL)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        Log.d("deeplink-status", uri.toString())
        startActivity(intent)


    }
}