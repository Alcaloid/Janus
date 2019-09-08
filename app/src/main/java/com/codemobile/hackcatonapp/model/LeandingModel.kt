package com.codemobile.hackcatonapp.model

import java.io.Serializable

data class LendingModel(
    var id: String? = null,
    val limit: Int? = null,
    val interest: Int? = null,
    val period: String? = null,
    var status: Boolean = false,
    var description: String? = null,
    var lenderName:String? = null,
    val userGet: ArrayList<String> = arrayListOf()
):Serializable
