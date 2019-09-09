package com.codemobile.hackcatonapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.codemobile.hackcatonapp.LENDER_DATABASE
import kotlinx.android.synthetic.main.fragment_loan.*
import com.codemobile.hackcatonapp.R
import com.codemobile.hackcatonapp.USER_DATABASE
import com.codemobile.hackcatonapp.USER_ID_LOANER
import com.codemobile.hackcatonapp.activity.LoanListActivity
import com.codemobile.hackcatonapp.activity.PaymentActivity
import com.codemobile.hackcatonapp.adapter.AccountAdapter
import com.codemobile.hackcatonapp.adapter.LeandingAdapter
import com.codemobile.hackcatonapp.interfaces.QueryUser
import com.codemobile.hackcatonapp.model.LendingModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class LoanFragment : Fragment() {

    private val moneyAccountArray: ArrayList<String> = arrayListOf("2000", "100", "3000")
    private val loaningArrayList: ArrayList<LendingModel> = arrayListOf()

    private var loaningAdapter: LeandingAdapter? = null
    private var accountAdapter: AccountAdapter? = null

    lateinit var database: FirebaseFirestore
    lateinit var LeandingRef: CollectionReference
    lateinit var UserRef: CollectionReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_loan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        setAccount(view)
        setLoan(view)
        notificationLoanOfUser()
        setOnAddLoaning()
    }

    private fun setLoan(_view: View) {
        loaningAdapter = LeandingAdapter(loaningArrayList,1,object : QueryUser {
            override fun queryUserData(userArrayList: ArrayList<String>, id: String?) {
                //set on click
            }
        })
        rcv_myLoaning.let {
            it.adapter = loaningAdapter
            it.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setOnAddLoaning() {
        btn_toLoadlist.setOnClickListener {
            val intent = Intent(context, LoanListActivity::class.java)
            startActivity(intent)
        }
        btnPayment.setOnClickListener {
            val intent = Intent(context, PaymentActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkUserLoan() {
        if (loaningArrayList.isEmpty()) {
            image_notLoanding?.visibility = View.VISIBLE
            txt_notLoan?.visibility = View.VISIBLE
        } else {
            image_notLoanding?.visibility = View.GONE
            txt_notLoan?.visibility = View.GONE
        }
    }

    fun notificationLoanOfUser() {
        //this notification all of them!!
        loaningArrayList.clear()
        LeandingRef.whereArrayContains("userGet", USER_ID_LOANER).addSnapshotListener { querySnapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }
            querySnapshot?.forEach {
                val result = it.toObject(LendingModel::class.java)
                loaningArrayList.add(result)
                loaningArrayList[loaningArrayList.lastIndex].lenderName = it.get("lender").toString()
            }
            queryLenderName()
            checkUserLoan()
            loaningAdapter?.notifyDataSetChanged()
        }
    }

    fun init() {
        database = FirebaseFirestore.getInstance()
        LeandingRef = database.collection(LENDER_DATABASE)
        UserRef = database.collection(USER_DATABASE)
    }

    private fun queryLenderName() {
        for (i in 0 until loaningArrayList.size-1) {
            getLenderName(loaningArrayList[i].lenderName.toString(),i)
        }
    }

    private fun getLenderName(id: String,index:Int){
        UserRef.document(id).get()
            .addOnSuccessListener { result ->
                val lenderName = result.get("Name")
                loaningArrayList[index].lenderName = lenderName.toString()
                loaningAdapter?.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Fail to read data", Toast.LENGTH_SHORT).show()
            }
    }

    private fun setAccount(_view: View) {
        accountAdapter =
            AccountAdapter(moneyAccountArray)
        rcv_loan_account.let {
            it.adapter = accountAdapter
            it.layoutManager = LinearLayoutManager(_view.context, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}