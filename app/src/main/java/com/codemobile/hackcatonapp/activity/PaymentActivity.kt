package com.codemobile.hackcatonapp.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class PaymentActivity : AppCompatActivity() {
    private var deeplink: String = "scbeasysim://billpayment-anonymous/d818750f-bf08-48eb-afdc-be9c425d381e"
    private var callbackURL: String = "?callback_url=https://easy-loan.com/loan"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uri = Uri.parse(deeplink + callbackURL)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        Log.d("status-s", uri.toString())
//        startActivity(intent)
    }
}