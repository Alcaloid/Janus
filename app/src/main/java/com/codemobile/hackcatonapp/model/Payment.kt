package com.codemobile.hackcatonapp.model

data class Payment(
    val accountTo: String,
    val merchantMetaData: MerchantMetaData,
    val paymentAmount: Int,
    val ref1: String,
    val transactionSubType: String,
    val transactionType: String
)

data class MerchantMetaData(
    val analytics: Analytics,
    val paymentInfo: List<PaymentInfo>
)

data class Analytics(
    val Detail1: String,
    val Detail2: String,
    val Detail3: String,
    val Detail4: String,
    val Detail5: String,
    val Detail6: String,
    val Partner: String,
    val Product_category: String,
    val Product_code: String
)

data class PaymentInfo(
    val description: String,
    val header: String,
    val imageUrl: String,
    val title: String,
    val type: String
)