package com.codemobile.hackcatonapp.model

import androidx.room.Entity

@Entity(tableName = "favorite")
data class LeandingModel(
    val limit:Int,
    val interrest:Int,
    val peroid:String,
    val status:String
)
