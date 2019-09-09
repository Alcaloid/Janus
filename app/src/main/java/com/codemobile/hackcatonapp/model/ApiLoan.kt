package com.codemobile.hackcatonapp.model

data class APILoan(
    val id: Int,
    val interest: Int,
    val limit: Int,
    val payerId: Int,
    val payerName: String,
    val period: Int
)