package com.codemobile.hackcatonapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codemobile.hackcatonapp.R
import com.codemobile.hackcatonapp.interfaces.QueryUser
import com.codemobile.hackcatonapp.model.LendingModel
import kotlinx.android.synthetic.main.card_my_lending.view.*
import java.text.DecimalFormat

class LeandingAdapter(val dataArrayList: ArrayList<LendingModel>,val role:Int, val queryUser: QueryUser) :
    RecyclerView.Adapter<LeandingHolder>() {

    var txt_color: Int = Color.RED
    var statusText: String = "Waiting"

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
        val formatter = DecimalFormat("#,###,###.##")
        val limit = formatter.format(dataArrayList[position].limit)
        holder.limit.text = "Limit: ${limit}฿"
        holder.interest.text = "Interest: ${dataArrayList[position].interest}%"
        holder.period.text = "Period: ${dataArrayList[position].period}"
        if (role == 0){
            setCardLender(holder,position)
        }else{
            setCardLoaning(holder,position)
        }
    }

    fun setCardLender(holder: LeandingHolder, position: Int){
        setStatusText(position)
        holder.name.visibility = View.GONE
        holder.status.setTextColor(txt_color)
        holder.status.text = statusText
        holder.itemView.setOnClickListener {
            if (dataArrayList[position].userGet.isNotEmpty() && !dataArrayList[position].status) {
                queryUser.queryUserData(dataArrayList[position].userGet, dataArrayList[position].id)
            }
        }
    }

    fun setCardLoaning(holder: LeandingHolder, position: Int){
        holder.detail.visibility = View.GONE
        holder.status.setTextColor(txt_color)
        holder.status.text = statusText
        holder.name.text = dataArrayList[position].lenderName
    }

    fun setStatusText(position: Int) {
        if (dataArrayList[position].status) {
            statusText = "Lending"
            txt_color = Color.GREEN
        } else if (dataArrayList[position].userGet.isNotEmpty()) {
            statusText = "Need Approve"
            txt_color = Color.YELLOW
        } else {
            statusText = "Waiting"
            txt_color = Color.RED
        }
    }
}

class LeandingHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name = view.txt_card_lender_lenderName
    val limit = view.txt_limit
    val interest = view.txt_interest
    val period = view.txt_period
    val status = view.txt_status
    val detail = view.txt_detail
}
