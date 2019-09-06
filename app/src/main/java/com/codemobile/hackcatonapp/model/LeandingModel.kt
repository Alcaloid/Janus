package com.codemobile.hackcatonapp.model

import java.io.Serializable

data class LeandingModel(
    val limit:Int,
    val interrest:Int,
    val peroid:String,
    var status:String
):Serializable
