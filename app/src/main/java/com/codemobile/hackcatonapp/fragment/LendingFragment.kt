package com.codemobile.hackcatonapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.codemobile.hackcatonapp.*
import com.codemobile.hackcatonapp.lendingactivity.AddLendingActivity
import com.codemobile.hackcatonapp.adapter.AccountAdapter
import com.codemobile.hackcatonapp.adapter.LeandingAdapter
import com.codemobile.hackcatonapp.interfaces.QueryUser
import com.codemobile.hackcatonapp.lendingactivity.ApproveActivity
import com.codemobile.hackcatonapp.model.LendingModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_lend.*

class LendingFragment : Fragment() {

    private val moneyAccountArray: ArrayList<String> = arrayListOf("100000")
    private val lendingArrayList: ArrayList<LendingModel> = arrayListOf()
    private var leandingAdapter: LeandingAdapter? = null
    private var accountAdapter: AccountAdapter? = null

    lateinit var database: FirebaseFirestore
    lateinit var LeandingRef: CollectionReference
    lateinit var UserRef:CollectionReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.codemobile.hackcatonapp.R.layout.fragment_lend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setAccount(view)
        setLending(view)
        notificationUserGetLending()
        notificationUserMoney()
        setOnAddLending()
    }

    private fun setLending(_view: View) {
        leandingAdapter =
            LeandingAdapter(lendingArrayList,0, object : QueryUser {
                override fun queryUserData(userArrayList: ArrayList<String>, id: String?) {
                    val intent: Intent = Intent(context, ApproveActivity::class.java)
                    intent.putExtra(USER_LIST, userArrayList)
                    intent.putExtra(LENDER_MONEY,moneyAccountArray[0])
                    intent.putExtra(LENDING_ID, id)
                    startActivity(intent)
                }
            })
        rcv_myLending.let {
            it.adapter = leandingAdapter
            it.layoutManager = LinearLayoutManager(_view.context)
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 1){
//            if (resultCode == 1){
//                val balaceMoney = data?.getStringExtra("result")
//                moneyAccountArray[0] = balaceMoney.toString()
//                accountAdapter?.notifyDataSetChanged()
//            }
//        }
//    }

    private fun setOnAddLending() {
        leandingAdapter?.notifyDataSetChanged()
        btn_addLending.setOnClickListener {
            val intent: Intent = Intent(context, AddLendingActivity::class.java)
//            intent.putExtra(LENDER_MONEY,moneyAccountArray[0])
            startActivity(intent)
//            startActivityForResult(intent,1)
        }
    }

    fun notificationUserGetLending() {
        //this notification all of them!!
        LeandingRef.whereEqualTo("lender", USER_ID_LENDER).addSnapshotListener { querySnapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }
            lendingArrayList.clear()
            querySnapshot?.forEach {
                val result = it.toObject(LendingModel::class.java)
                lendingArrayList.add(result)
                lendingArrayList[lendingArrayList.lastIndex].id = it.id
            }
            showImageArrayEmpty()
            leandingAdapter?.notifyDataSetChanged()
        }
    }

    fun notificationUserMoney(){
        //notification money of user
        UserRef.document(USER_ID_LENDER).addSnapshotListener { snapshot, e ->
            if (e != null){
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()){
                moneyAccountArray[0] = snapshot["Money"].toString()
            }
            accountAdapter?.notifyDataSetChanged()
        }
    }

    fun showImageArrayEmpty() {
        if (lendingArrayList.isNotEmpty()) {
            image_notLeanding?.visibility = View.GONE
            txt_notLeanding?.visibility = View.GONE
        } else {
            image_notLeanding?.visibility = View.VISIBLE
            txt_notLeanding?.visibility = View.VISIBLE
        }
    }

    fun init() {
        database = FirebaseFirestore.getInstance()
        LeandingRef = database.collection(LENDER_DATABASE)
        UserRef = database.collection(USER_DATABASE)
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