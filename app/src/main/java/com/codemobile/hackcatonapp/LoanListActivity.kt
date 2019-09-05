package com.codemobile.hackcatonapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codemobile.hackcatonapp.LoanListActivity.*
import kotlinx.android.synthetic.main.activity_loan_list.*
import kotlinx.android.synthetic.main.loan_list_card.view.*
import java.text.DecimalFormat

class LoanListActivity() : AppCompatActivity(), OnLoanClick {
    private lateinit var loanListAdaptor: LoanListAdapter

    private var loanArray: ArrayList<Loan> = arrayListOf(
        Loan(100000.00, 3, 3, "Waramporn Yangkijkarn"),
        Loan(200000.00, 2, 4, "Mikasa Ackerman"),
        Loan(6000000.00, 5, 5, "Tanjirou Kamado"),
        Loan(800000.00, 5, 5, "Nezuko Kamado"),
        Loan(2000000.00, 5, 5, "Kotarou Kashima"),
        Loan(100000.00, 5, 5, "Ryuichi Kashima"),
        Loan(900000.00, 5, 5, "Phichit Chulanon")

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan_list)

        loanListAdaptor = LoanListAdapter(this)
        rvLoanListView.adapter = loanListAdaptor
        rvLoanListView.layoutManager = LinearLayoutManager(this)

        feedLoanData()
    }

    private fun feedLoanData() {
        loanListAdaptor.notifyDataSetChanged()
    }

    override fun onLoanClick(loan: Loan) {
        var intent = Intent(this, LoanDetailActivity::class.java)
        startActivity(intent)
    }


    inner class LoanListAdapter(private val listener: OnLoanClick) : RecyclerView.Adapter<loanListHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): loanListHolder {
            return loanListHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.loan_list_card,
                    parent,
                    false
                )
            )
        }

        override fun getItemCount(): Int {
            return loanArray.size
        }

        override fun onBindViewHolder(holder: loanListHolder, position: Int) {
            val item = loanArray[position]

            val formatter = DecimalFormat("#,###,###")
            val limit = formatter.format(item.limit)
            holder.limitLoan.text = "Limit:  ${limit}฿"
            //String.format("%.2f",getString(R.string.laon_list_limit, item.limit))
            holder.interestLoan.text = "Interest:  ${item.interest}%"
            //getString(R.string.laon_list_interest,item.interest)
            holder.periodLoan.text = getString(R.string.laon_list_period, item.period)
            holder.itemView.setOnClickListener {
                listener.onLoanClick(item)
            }
        }

    }

    class loanListHolder(view: View) : RecyclerView.ViewHolder(view) {

        var limitLoan: TextView = view.limitLoan
        var interestLoan: TextView = view.interestLoan
        var periodLoan: TextView = view.periodLoan
        var lonerName: TextView = view.lonerName

    }

    data class Loan(
        val limit: Double,
        val interest: Int,
        val period: Int,
        val loner: String
    )
}

interface OnLoanClick {
    fun onLoanClick(loan: Loan)
}
