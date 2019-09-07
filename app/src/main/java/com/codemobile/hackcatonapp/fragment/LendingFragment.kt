package com.codemobile.hackcatonapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.codemobile.hackcatonapp.LENDING_ID
import com.codemobile.hackcatonapp.USER_LIST
import com.codemobile.hackcatonapp.lendingactivity.AddLendingActivity
import com.codemobile.hackcatonapp.adapter.AccountAdapter
import com.codemobile.hackcatonapp.adapter.LeandingAdapter
import com.codemobile.hackcatonapp.interfaces.QueryUser
import com.codemobile.hackcatonapp.lendingactivity.ApproveActivity
import com.codemobile.hackcatonapp.model.LendingModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_lend.*

class LendingFragment : Fragment(){

    private val moneyAccountArray: ArrayList<String> = arrayListOf("100000", "2000", "10000")
    private val lendingArrayList: ArrayList<LendingModel> = arrayListOf()
    private var leandingAdapter: LeandingAdapter? = null
    private var accountAdapter: AccountAdapter? = null

    lateinit var database: FirebaseFirestore
    lateinit var LeandingRef: CollectionReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.codemobile.hackcatonapp.R.layout.fragment_lend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        setAccount(view)
        setLending(view)
//        checkLendingData()
        notificationUserGetLending()
        setOnAddLending()
    }

    private fun setLending(_view: View) {
        leandingAdapter =
            LeandingAdapter(lendingArrayList,object : QueryUser {
                override fun queryUserData(userArrayList:ArrayList<String>,id:String?) {
                    val intent:Intent = Intent(context,ApproveActivity::class.java)
                    intent.putExtra(USER_LIST,userArrayList)
                    intent.putExtra(LENDING_ID,id)
                    startActivity(intent)
                }
            })
        rcv_myLending.let {
            it.adapter = leandingAdapter
            it.layoutManager = LinearLayoutManager(_view.context)
        }
    }

    private fun setOnAddLending() {
        leandingAdapter?.notifyDataSetChanged()
        btn_addLending.setOnClickListener {
            startActivity(Intent(context, AddLendingActivity::class.java))
        }
    }

    fun checkLendingData(){
        //query data to add array first time
        LeandingRef.whereEqualTo("lender", "0").get().addOnSuccessListener { documentSnapshot ->
            lendingArrayList.clear()
            documentSnapshot.forEach {
                //change Query data to LeandingModel
                val result = it.toObject(LendingModel::class.java)
                lendingArrayList.add(result)
            }
            if (lendingArrayList.isNotEmpty()){
                image_notLeanding.visibility = View.GONE
                txt_notLeanding.visibility = View.GONE
            }else{
                image_notLeanding.visibility = View.VISIBLE
                txt_notLeanding.visibility = View.VISIBLE
            }
            lendingArrayList.forEach {
                println("Query:"+it)
            }
            leandingAdapter?.notifyDataSetChanged()
        }
    }

    fun notificationUserGetLending(){
        //this notification all of them!!
        LeandingRef.whereEqualTo("lender", "0").addSnapshotListener { querySnapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }
            lendingArrayList.clear()
            querySnapshot?.forEach {
                val result = it.toObject(LendingModel::class.java)
                lendingArrayList.add(result)
                lendingArrayList[lendingArrayList.lastIndex].id = it.id
            }
            leandingAdapter?.notifyDataSetChanged()
        }
    }

    fun init() {
        database = FirebaseFirestore.getInstance()
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