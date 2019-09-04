package com.codemobile.hackcatonapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.codemobile.hackcatonapp.R
import com.codemobile.hackcatonapp.adapter.AccountAdapter
import kotlinx.android.synthetic.main.fragment_lend.*

class LendingFragment: Fragment(){

    private val moneyAccountArray:ArrayList<String> = arrayListOf("100000","2000","10000")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val accountAdapter: AccountAdapter =
            AccountAdapter(moneyAccountArray)
        rcv_account.let {
            it.adapter = accountAdapter
            it.layoutManager = LinearLayoutManager(view.context,LinearLayoutManager.HORIZONTAL,false)
        }

    }
}