package com.codemobile.hackcatonapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Loan(
    val limit: Double,
    val interest: Int,
    val period: Int,
    val loner: String
) : Parcelable