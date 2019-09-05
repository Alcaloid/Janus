package com.codemobile.hackcatonapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codemobile.hackcatonapp.R
import com.codemobile.hackcatonapp.model.LeandingModel
import kotlinx.android.synthetic.main.card_my_lending.view.*

class LeandingAdapter(val dataArrayList:ArrayList<LeandingModel>) : RecyclerView.Adapter<LeandingHolder>(){
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
        holder.limit.text = dataArrayList[position].limit.toString()
        holder.interest.text = dataArrayList[position].interrest.toString()
        holder.period.text = dataArrayList[position].peroid
        holder.status.text = dataArrayList[position].status
    }

}

class LeandingHolder(view: View): RecyclerView.ViewHolder(view) {
    val limit = view.txt_limit
    val interest = view.txt_interest
    val period = view.txt_period
    val status = view.txt_status
}
