package com.codemobile.hackcatonapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codemobile.hackcatonapp.R
import com.codemobile.hackcatonapp.interfaces.UpdateApprove
import com.codemobile.hackcatonapp.model.UserModel
import kotlinx.android.synthetic.main.card_need_approve.view.*

class NeedApproveAdapter(val dataArrayList: ArrayList<UserModel>, val updateApprove: UpdateApprove) :
    RecyclerView.Adapter<UserInformationHolder>() {

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
        val item = dataArrayList[position]
        holder.name.text = item.Name
        holder.income.text = "Income:  ${item.Income}฿ per month"
        holder.job.text = "Job Title:  ${item.Job}"
        holder.company.text = "Company name:  ${item.Company}"
        holder.address.text = "Address:  ${item.Address}"
        holder.sourceIncome.text = "Source of income:  ${item.SourceIncome}"
        holder.history.text = "Loan/ Repayment history:  ${item.LoanRepaymentHistory}"
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
    val address = view.txt_address
    val sourceIncome = view.txt_source_income
    val history = view.txt_history
}
