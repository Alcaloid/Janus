package com.codemobile.hackcatonapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codemobile.hackcatonapp.R
import kotlinx.android.synthetic.main.custom_card_account.view.*
import java.text.DecimalFormat

class AccountAdapter(val dataArrayList: ArrayList<String>) : RecyclerView.Adapter<AccountHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountHolder {
        return AccountHolder(
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

    override fun onBindViewHolder(holder: AccountHolder, position: Int) {
//        val formatter = DecimalFormat("#,###,###.##")
//        val money = formatter.format(dataArrayList[position])
        holder.account.text = "xxx-xxx000-1"
        holder.money.text = dataArrayList[position]
    }

}

class AccountHolder(view: View) : RecyclerView.ViewHolder(view) {

    val account = view.txt_num_account
    val money = view.txt_money
}
