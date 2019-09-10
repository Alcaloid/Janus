package com.codemobile.hackcatonapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.codemobile.hackcatonapp.LENDER_DATABASE
import com.codemobile.hackcatonapp.USER_DATABASE
import com.codemobile.hackcatonapp.USER_ID_LOANER
import com.codemobile.hackcatonapp.adapter.LoanListAdapter
import com.codemobile.hackcatonapp.adapter.OnLoanClick
import com.codemobile.hackcatonapp.model.LendingModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_loan_list.*


class LoanListActivity() : AppCompatActivity(), OnLoanClick {
    lateinit var database: FirebaseFirestore
    lateinit var LeandingRef: CollectionReference
    lateinit var UserRef: CollectionReference

    private var lenderArray: ArrayList<LendingModel> = arrayListOf()
    private var loanListAdaptor: LoanListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.codemobile.hackcatonapp.R.layout.activity_loan_list)

        initAdapter()
        initDatabase()
        queryLenderList()

        btn_image_backToLoan.setOnClickListener {
            this.finish()
        }
    }

    private fun queryLenderName() {
        for (i in 0 until lenderArray.size - 1) {
            getLenderName(lenderArray[i].lenderName.toString(), i)
        }
        loanListAdaptor?.submitList(lenderArray)
    }

    private fun initDatabase() {
        database = FirebaseFirestore.getInstance()
        LeandingRef = database.collection(LENDER_DATABASE)
        UserRef = database.collection(USER_DATABASE)
    }

    private fun queryLenderList() {
        lenderArray.clear()
        LeandingRef.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val lendingData = document.toObject(LendingModel::class.java)
                    if (lendingData.id != USER_ID_LOANER && !lendingData.status) {
                        lenderArray.add(lendingData)
                        lenderArray[lenderArray.lastIndex].lenderName = document.get("lenderName").toString()
                        lenderArray[lenderArray.lastIndex].id = document.id
                    }
                }
                loanListAdaptor?.submitList(lenderArray)
//                queryLenderName()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Fail to read data", Toast.LENGTH_SHORT).show()
            }
    }

    private fun getLenderName(id: String, index: Int) {
        UserRef.document(id).get()
            .addOnSuccessListener { result ->
                val lenderName = result.get("Name")
                lenderArray[index].lenderName = lenderName.toString()
                loanListAdaptor?.submitList(lenderArray)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Fail to read data", Toast.LENGTH_SHORT).show()
            }
    }

    private fun initAdapter() {
        loanListAdaptor = LoanListAdapter(this)
        rvLoanListView.let {
            it.adapter = loanListAdaptor
            it.layoutManager = LinearLayoutManager(this)
        }
    }

    override fun onLoanClick(loan: LendingModel) {
        val intent = Intent(this, LoanDetailActivity::class.java)
        intent.putExtra("Loan", loan)
        startActivity(intent)
        finish()
    }

}

