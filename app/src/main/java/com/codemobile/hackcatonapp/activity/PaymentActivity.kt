package com.codemobile.hackcatonapp.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.codemobile.hackcatonapp.R
import com.codemobile.hackcatonapp.model.ApiInterface
import com.codemobile.hackcatonapp.model.Token
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        getApiMobileList()
    }

    fun getApiMobileList() {

        val call = ApiInterface.getClient().accessToken()
        call.enqueue(
            object : Callback<Token> {
                override fun onFailure(call: Call<Token>, t: Throwable) {
                    Log.d("getApi-e", t.message.toString())

                }

                override fun onResponse(call: Call<Token>, response: Response<Token>) {
                    Log.d("getApi", response.toString())
                    if (response.isSuccessful) {

                    }
                }
            })
    }
}