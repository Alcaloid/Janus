package com.codemobile.hackcatonapp.model

data class LendingModel(
    val limit:Int? = null,
    val interest:Int? = null,
    val period:String? = null,
    var status:String? = null,
    val userGet:ArrayList<Int> = arrayListOf()
)
