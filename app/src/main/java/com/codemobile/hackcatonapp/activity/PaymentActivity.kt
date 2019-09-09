package com.codemobile.hackcatonapp.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.codemobile.hackcatonapp.R
import com.codemobile.hackcatonapp.model.APILoan
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

        val call = ApiInterface.getClient().getLoan()
        call.enqueue(
            object : Callback<List<APILoan>> {
                override fun onFailure(call: Call<List<APILoan>>, t: Throwable) {
                    Log.d("getApi-e", t.message.toString())

                }

                override fun onResponse(call: Call<List<APILoan>>, response: Response<List<APILoan>>) {
                    Log.d("getApi", response.toString())
                    if (response.isSuccessful) {

                    }
                }
            })
    }
}