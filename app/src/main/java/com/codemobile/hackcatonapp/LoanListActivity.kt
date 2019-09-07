package com.codemobile.hackcatonapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.codemobile.hackcatonapp.adapter.LoanListAdapter
import com.codemobile.hackcatonapp.adapter.OnLoanClick
import com.codemobile.hackcatonapp.model.Loan
import kotlinx.android.synthetic.main.activity_loan_list.*

class LoanListActivity() : AppCompatActivity(), OnLoanClick {
    private lateinit var loanListAdaptor: LoanListAdapter

    private var loanArray: ArrayList<Loan> = arrayListOf(
        Loan(100000.00, 3, 3, "Waramporn Yangkijkarn"),
        Loan(200000.00, 2, 4, "Mikasa Ackerman"),
        Loan(6000000.93, 5, 5, "Tanjirou Kamado"),
        Loan(800000.00, 5, 5, "Nezuko Kamado"),
        Loan(2000000.60, 5, 5, "Kotarou Kashima"),
        Loan(100000.00, 5, 5, "Ryuichi Kashima"),
        Loan(900000.00, 5, 5, "Phichit Chulanon")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan_list)

        loanListAdaptor = LoanListAdapter(this)
        rvLoanListView.adapter = loanListAdaptor
        rvLoanListView.layoutManager = LinearLayoutManager(this)

        btn_image_backToLoan.setOnClickListener {
            finish()
        }

        feedLoanData()
    }

    private fun feedLoanData() {
        //will get data from service and assign to loanArray
        loanListAdaptor.submitList(loanArray)
    }

    override fun onLoanClick(loan: Loan) {
        var intent = Intent(this, LoanDetailActivity::class.java)
        intent.putExtra("Loan", loan)
        startActivity(intent)
    }

}

