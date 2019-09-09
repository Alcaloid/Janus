package com.codemobile.hackcatonapp.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
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

}