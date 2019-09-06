package com.codemobile.hackcatonapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.codemobile.hackcatonapp.AddLendingActivity
import com.codemobile.hackcatonapp.adapter.AccountAdapter
import com.codemobile.hackcatonapp.adapter.LeandingAdapter
import com.codemobile.hackcatonapp.model.LeandingModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_lend.*



class LendingFragment : Fragment() {

    private val moneyAccountArray: ArrayList<String> = arrayListOf("100000", "2000", "10000")
    private val lendingArrayList: ArrayList<LeandingModel> = arrayListOf()
    private var leandingAdapter: LeandingAdapter? = null
    private var accountAdapter: AccountAdapter? = null

    lateinit var database: FirebaseFirestore
    lateinit var UserRef: CollectionReference
    lateinit var LeandingRef: CollectionReference
    private var hashMap: HashMap<Any, Any> = HashMap()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.codemobile.hackcatonapp.R.layout.fragment_lend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        setAccount(view)
        setLending(view)
        setOnAddLending()
    }

    private fun setLending(_view: View) {
        leandingAdapter =
            LeandingAdapter(lendingArrayList)
        rcv_myLending.let {
            it.adapter = leandingAdapter
            it.layoutManager = LinearLayoutManager(_view.context)
        }
    }

    private fun setOnAddLending() {
        if (lendingArrayList.isEmpty()) {
            image_notLeanding.visibility = View.VISIBLE
            txt_notLeanding.visibility = View.VISIBLE
        }
        leandingAdapter?.notifyDataSetChanged()
        btn_addLending.setOnClickListener {
            startActivityForResult(Intent(context, AddLendingActivity::class.java), 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            LeandingRef.whereEqualTo("Lender", "0").get()
                .addOnSuccessListener { documentSnapshot ->
                    documentSnapshot.forEach {
                        println(it.data)
                    }
                    leandingAdapter?.notifyDataSetChanged()
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(context, "Read Failed", Toast.LENGTH_SHORT).show()
                }
        }
    }

    fun init() {
        database = FirebaseFirestore.getInstance()
        UserRef = database.collection("User")
        LeandingRef = database.collection("Leanding")
    }

    private fun setAccount(_view: View) {
        accountAdapter =
            AccountAdapter(moneyAccountArray)
        rcv_account.let {
            it.adapter = accountAdapter
            it.layoutManager = LinearLayoutManager(_view.context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

}