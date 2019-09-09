package com.codemobile.hackcatonapp.model

data class UserModel(
    var id: String? = null,
    val Name: String? = null,
    val Income: Int? = null,
    val Job: String? = null,
    val Company: String? = null,
    val Address: String? = null,
    val LoanRepaymentHistory: String? = null,
    val SourceIncome: String? = null
)