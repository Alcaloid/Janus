package com.codemobile.hackcatonapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codemobile.hackcatonapp.R
import com.codemobile.hackcatonapp.interfaces.UpdateApprove
import com.codemobile.hackcatonapp.model.UserModel
import kotlinx.android.synthetic.main.card_need_approve.view.*

class NeedApproveAdapter(val dataArrayList: ArrayList<UserModel>,val updateApprove: UpdateApprove) : RecyclerView.Adapter<UserInformationHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserInformationHolder {
        return UserInformationHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_need_approve,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataArrayList.size
    }

    override fun onBindViewHolder(holder: UserInformationHolder, position: Int) {
        holder.name.text = dataArrayList[position].Name
        holder.income.text = "Income: ${dataArrayList[position].Income}฿ per month"
        holder.job.text = "Job Title: ${dataArrayList[position].Job}"
        holder.company.text = "Company name:${dataArrayList[position].Company}"
        holder.approve.setOnClickListener {
            updateApprove.updateLending(dataArrayList[position].id as String)
        }
    }

}

class UserInformationHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name = view.txt_username
    val income = view.txt_need_approve_income
    val job = view.txt_need_approve_job
    val company = view.txt_need_approve_company
    val approve = view.btn_approve
}
