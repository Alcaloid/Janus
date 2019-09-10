package com.codemobile.hackcatonapp.model

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import com.google.gson.JsonObject


interface ApiInterface {

    @POST("/easyloan/createDeeplink")
    fun accessToken(@Body dataBody:JsonObject): Call<JsonObject>

    @POST("/v2/deeplink/transactions")
    fun getImage(): Call<Payment>

    companion object Factory {
        private const val BASE_URL = "http://192.168.101.177:4567"
        private var retrofit: Retrofit? = null
        fun getClient(): ApiInterface {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!.create(ApiInterface::class.java)
        }
    }
}
