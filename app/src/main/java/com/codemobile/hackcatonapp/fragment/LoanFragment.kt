package com.codemobile.hackcatonapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.codemobile.hackcatonapp.LoanListActivity
import kotlinx.android.synthetic.main.fragment_loan.*
import com.codemobile.hackcatonapp.R
import com.codemobile.hackcatonapp.adapter.AccountAdapter
import com.codemobile.hackcatonapp.model.LeandingModel

class LoanFragment:Fragment() {

    private val moneyAccountArray:ArrayList<String> = arrayListOf("100000","2000","10000")
    private val loaningArrayList:ArrayList<LeandingModel> = arrayListOf()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_loan, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAccount(view)
        setLending(view)
        setOnAddLending()
    }

    private fun setLending(_view: View) {
        rcv_myLoaning.let {
        }
    }

    private fun setOnAddLending() {
        if (loaningArrayList.isEmpty()){
            image_notLoanding.visibility = View.VISIBLE
            txt_notLoan.visibility = View.VISIBLE
        }else{
            image_notLoanding.visibility = View.GONE
            txt_notLoan.visibility = View.GONE
        }
        btn_toLoadlist.setOnClickListener {
            val intent = Intent(context, LoanListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setAccount(_view: View){
        val accountAdapter: AccountAdapter =
            AccountAdapter(moneyAccountArray)
        rcv_loan_account.let {
            it.adapter = accountAdapter
            it.layoutManager = LinearLayoutManager(_view.context,LinearLayoutManager.HORIZONTAL,false)
        }
    }
}