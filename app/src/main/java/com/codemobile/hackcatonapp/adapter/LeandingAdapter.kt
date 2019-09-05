package com.codemobile.hackcatonapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codemobile.hackcatonapp.R
import kotlinx.android.synthetic.main.custom_card_account.view.*

class LeandingAdapter(val dataArrayList:ArrayList<String>) : RecyclerView.Adapter<LeandingHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeandingHolder {
        return LeandingHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.custom_card_account,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataArrayList.size
    }

    override fun onBindViewHolder(holder: LeandingHolder, position: Int) {
        holder.account.text = "xxx-xxx000-1"
        holder.money.text   = dataArrayList[position]
    }

}

class LeandingHolder(view: View): RecyclerView.ViewHolder(view) {

    val account = view.txt_num_account
    val money   = view.txt_money
}