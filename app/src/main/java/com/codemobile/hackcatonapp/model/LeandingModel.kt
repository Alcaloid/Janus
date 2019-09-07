package com.codemobile.hackcatonapp.model

data class LendingModel(
    var id: String? = null,
    val limit: Int? = null,
    val interest: Int? = null,
    val period: String? = null,
    var status: Boolean = false,
    var description: String? = null,
    val userGet: ArrayList<String> = arrayListOf()
)
