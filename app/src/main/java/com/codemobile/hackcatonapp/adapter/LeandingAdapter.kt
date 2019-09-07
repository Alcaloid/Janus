package com.codemobile.hackcatonapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codemobile.hackcatonapp.R
import com.codemobile.hackcatonapp.model.LendingModel
import kotlinx.android.synthetic.main.card_my_lending.view.*

class LeandingAdapter(val dataArrayList:ArrayList<LendingModel>) : RecyclerView.Adapter<LeandingHolder>(){

    var txt_color:Int = Color.RED
    var isPeopleGet = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeandingHolder {
        return LeandingHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_my_lending,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataArrayList.size
    }

    override fun onBindViewHolder(holder: LeandingHolder, position: Int) {
        holder.limit.text = "Limit: ${dataArrayList[position].limit}฿"
        holder.interest.text = "Interest: ${dataArrayList[position].interest}%"
        holder.period.text = "Period: ${dataArrayList[position].period}"
        holder.status.text = dataArrayList[position].status
        holder.status.setTextColor(txt_color)

        holder.itemView.setOnClickListener {
            if (isPeopleGet){
                dataArrayList[position].status = "LENDED"
                txt_color = Color.GREEN
                isPeopleGet = false
            }else{
                dataArrayList[position].status = "NEED APPROVE"
                txt_color = Color.YELLOW
                isPeopleGet = true
            }
            notifyDataSetChanged()
        }
    }

}

class LeandingHolder(view: View): RecyclerView.ViewHolder(view) {
    val limit = view.txt_limit
    val interest = view.txt_interest
    val period = view.txt_period
    val status = view.txt_status
}
