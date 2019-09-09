package com.codemobile.hackcatonapp.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_loaner_payment_lender.view.*
import kotlinx.android.synthetic.main.card_my_lending.view.*

class LeandingHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name = view.txt_card_lender_lenderName
    val limit = view.txt_limit
    val interest = view.txt_interest
    val period = view.txt_period
    val status = view.txt_status
    val detail = view.txt_detail
}

class LoanerHolder(view: View) : RecyclerView.ViewHolder(view) {
    val pay_amount = view.txt_payment_amount
    val pay_interest = view.txt_payment_interest
    val pay_duedate = view.txt_payment_date
    val pay_status = view.txt__payment_status
    val pay_button = view.btn_payment_pay
    val pay_total = view.txt_payment_total
}