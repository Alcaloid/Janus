package com.codemobile.hackcatonapp.model

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface {
//https://api.partners.scb/partners/sandbox/v1/oauth/token
//https://api.partners.scb/partners/sandbox/v2/deeplink/transactions
//    requestUId, resourceOwnerId
//    @Header("requestUId : b0ea1f3f-e41d-4727-a078-47b15e9bd0ef", "resourceOwnerId : l7403bd3fce86f451eb386765a781091ec")
//    @POST("/v1/oauth/token")
//    fun accessToken(): Call<Token>
//
//    @POST("/v2/deeplink/transactions")
//    fun getImage():Call<Payment>
    @GET("/easyloan/getLoan")
    fun getLoan(): Call<List<APILoan>>

    companion object Factory {
        private const val BASE_URL = "http://192.168.101.177:4567/"
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
