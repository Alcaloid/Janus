package com.codemobile.hackcatonapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.util.Log
import com.codemobile.hackcatonapp.LENDER_DATABASE
import com.codemobile.hackcatonapp.R
import com.codemobile.hackcatonapp.USER_ID_LOANER
import com.codemobile.hackcatonapp.model.LendingModel
import com.codemobile.hackcatonapp.model.Loan
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_loan_detail.*
import java.text.DecimalFormat

class LoanDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan_detail)

        val loan = intent.getSerializableExtra("Loan") as LendingModel

        val formatter = DecimalFormat("#,###,###.##")
        val limit = formatter.format(loan.limit)
        limitLoan.text = getString(R.string.loan_list_limit, limit)
        interestLoan.text = getString(R.string.laon_list_interest, loan.interest)
        periodLoan.text = "Period: ${loan.period} month"//getString(R.string.loan_list_period, loan.period)
        lonerName.text = loan.lenderName

        submitLoanBtn.setOnClickListener {
            loan.id?.let { it1 -> updateUserGet(it1) }
        }
        btn_image_backToLoanList.setOnClickListener {
            val intent = Intent(this, LoanListActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun updateUserGet(id:String) {
        FirebaseFirestore.getInstance().collection(LENDER_DATABASE).document(id).update("UserLeander", FieldValue.arrayUnion(
            USER_ID_LOANER))
    }

}
