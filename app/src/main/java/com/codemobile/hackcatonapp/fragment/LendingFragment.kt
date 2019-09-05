package com.codemobile.hackcatonapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.codemobile.hackcatonapp.R
import com.codemobile.hackcatonapp.adapter.AccountAdapter
import com.codemobile.hackcatonapp.model.LeandingModel
import kotlinx.android.synthetic.main.fragment_lend.*

class LendingFragment: Fragment(){

    private val moneyAccountArray:ArrayList<String> = arrayListOf("100000","2000","10000")
    private val lendingArrayList:ArrayList<LeandingModel> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAccount(view)
        setLending(view)
        setOnAddLending()
    }

    private fun setLending(_view: View) {
        val accountAdapter: AccountAdapter =
            AccountAdapter(moneyAccountArray)
        rcv_account.let {
            it.adapter = accountAdapter
            it.layoutManager = LinearLayoutManager(_view.context,LinearLayoutManager.HORIZONTAL,false)
        }
    }

    private fun setOnAddLending() {
        btn_addLending.setOnClickListener {
            image_notLending.visibility = View.GONE
            txt_notLeanding.visibility = View.GONE
        }
    }

    private fun setAccount(_view: View){
        val accountAdapter: AccountAdapter =
            AccountAdapter(moneyAccountArray)
        rcv_account.let {
            it.adapter = accountAdapter
            it.layoutManager = LinearLayoutManager(_view.context,LinearLayoutManager.HORIZONTAL,false)
        }
    }
}