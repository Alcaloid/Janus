package com.codemobile.hackcatonapp.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codemobile.hackcatonapp.USER_ID_LOANER
import com.codemobile.hackcatonapp.VIEW_CARD_LENDER
import com.codemobile.hackcatonapp.VIEW_CARD__LENDER_APPROVE
import com.codemobile.hackcatonapp.activity.PaymentActivity
import com.codemobile.hackcatonapp.interfaces.QueryUser
import com.codemobile.hackcatonapp.model.LendingModel
import java.text.DecimalFormat


class LoanerAdapter(
    private val context: Context,
    val dataArrayList: ArrayList<LendingModel>,
    val role: Int,
    val queryUser: QueryUser
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var deeplink: String = "scbeasysim://billpayment-anonymous/2af724a9-de99-4dba-adee-f0565140b1d3"
    private var callbackURL: String = "?callback_url=https://easy-loan.com/loan"

    var txt_color: Int = Color.RED
    var statusText: String = "Waiting"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            return LoanerHolder(
                LayoutInflater.from(parent.context).inflate(
                    com.codemobile.hackcatonapp.R.layout.card_loaner_payment_lender,
                    parent,
                    false
                )
            )
        } else {
            return LeandingHolder(
                LayoutInflater.from(parent.context).inflate(
                    com.codemobile.hackcatonapp.R.layout.card_my_lending,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (checkLenderApprove(position)) {
            return VIEW_CARD__LENDER_APPROVE
        } else {
            return VIEW_CARD_LENDER
        }
    }

    private fun checkLenderApprove(position: Int): Boolean {
        return dataArrayList[position].status && dataArrayList[position].userGet[0] == USER_ID_LOANER
    }

    override fun getItemCount(): Int {
        return dataArrayList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> {
                val viewHolder = holder as LeandingHolder
                setCardNotApprove(viewHolder, position)
            }
            1 -> {
                val viewHolder = holder as LoanerHolder
                setCardPayment(viewHolder, position)
            }
        }

    }

    private fun setCardPayment(holder: LoanerHolder, position: Int) {
        val formatter = DecimalFormat("#,###,###.##")
        val money = formatter.format(dataArrayList[position].limit)
        holder.pay_amount.text = "${money}฿"
        holder.pay_interest.text = "interest: ${dataArrayList[position].interest.toString()}%"
        holder.pay_status.text = "Approved"
        holder.pay_status.setTextColor(Color.rgb(54, 172, 19))
        holder.pay_duedate.text = "Due Date: ${dataArrayList[position].period}"
        holder.pay_total.text = "Total: ${getTotalPayment(position)}"
        holder.pay_button.setOnClickListener {
            if (dataArrayList[position].lender != null) {
                queryUser.queryUserData(arrayListOf(dataArrayList[position].lender!!), dataArrayList[position].id)
            }
            val uri = Uri.parse(deeplink+callbackURL)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            Log.d("deeplink-loan", uri.toString())
            context.startActivity(intent)
        }
    }

    private fun getTotalPayment(position: Int): String {
        val principleMoney = dataArrayList[position].limit as Int
        val interest = dataArrayList[position].interest as Int
        return (principleMoney + (principleMoney * interest) / 100).toString()
    }

    private fun setCardNotApprove(holder: LeandingHolder, position: Int) {
        val formatter = DecimalFormat("#,###,###.##")
        val limit = formatter.format(dataArrayList[position].limit)
        holder.limit.text = "Limit: ${limit}฿"
        holder.interest.text = "Interest: ${dataArrayList[position].interest}%"
        holder.period.text = "Period: ${dataArrayList[position].period}"
        if (role == 0) {
            setCardLender(holder, position)
        } else {
            setCardLoaning(holder, position)
        }
    }

    fun setCardLender(holder: LeandingHolder, position: Int) {
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

    fun setCardLoaning(holder: LeandingHolder, position: Int) {
        holder.detail.visibility = View.GONE
        holder.status.setTextColor(txt_color)
        holder.status.text = statusText
        holder.name.text = dataArrayList[position].lenderName
    }

    fun setStatusText(position: Int) {
        if (dataArrayList[position].status) {
            statusText = "Lending"
            txt_color = Color.rgb(54, 172, 19)
        } else if (dataArrayList[position].userGet.isNotEmpty()) {
            statusText = "Need Approve"
            txt_color = Color.rgb(240, 111, 0)
        } else {
            statusText = "Waiting"
            txt_color = Color.rgb(172, 19, 19)
        }
    }
}

