package com.codemobile.hackcatonapp.adapter;

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codemobile.hackcatonapp.R
import com.codemobile.hackcatonapp.model.LendingModel
import java.text.DecimalFormat


class LoanListAdapter(private val listener: OnLoanClick) : RecyclerView.Adapter<LoanListHolder>() {

    val loanArray: ArrayList<LendingModel>
        get() = _loanArray

    private var _loanArray: ArrayList<LendingModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LoanListHolder(parent)

    override fun getItemCount(): Int {
        return loanArray.size
    }

    override fun onBindViewHolder(holder: LoanListHolder, position: Int) {
        val item = _loanArray[position]

        val formatter = DecimalFormat("#,###,###.##")
        val limit = formatter.format(item.limit)
        holder.limitLoan.text = "Limit:  ${limit}฿"
        holder.interestLoan.text = "Interest:  ${item.interest}%"
        holder.periodLoan.text = "Period:  ${item.period} month"
        holder.lonerName.text = item.lenderName
        holder.itemView.setOnClickListener {
            listener.onLoanClick(item)
        }
    }

    fun submitList(list: ArrayList<LendingModel>) {
        _loanArray = list
        notifyDataSetChanged()
    }

}

class LoanListHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.loan_list_card, parent, false)
) {
    val limitLoan: TextView = itemView.findViewById(R.id.limitLoan)
    val interestLoan: TextView = itemView.findViewById(R.id.interestLoan)
    val periodLoan: TextView = itemView.findViewById(R.id.periodLoan)
    val lonerName: TextView = itemView.findViewById(R.id.lonerName)
}

interface OnLoanClick {
    fun onLoanClick(loan: LendingModel)
}