package com.codemobile.hackcatonapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_loan_detail.*
import java.text.DecimalFormat

class LoanDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan_detail)

        val loan = intent.getParcelableExtra<Loan>("Loan") ?: return

        val formatter = DecimalFormat("#,###,###.##")
        val limit = formatter.format(loan.limit)
        limitLoan.text = getString(R.string.loan_list_limit, limit)
        interestLoan.text = getString(R.string.laon_list_interest, loan.interest)
        periodLoan.text = getString(R.string.loan_list_period, loan.period)
        lonerName.text = loan.loner

        submitLoanBtn.setOnClickListener {
            Log.d("test", "submitLoanBtn click")
        }


    }
}
