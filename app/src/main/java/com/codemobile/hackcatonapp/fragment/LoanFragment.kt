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
import com.codemobile.hackcatonapp.adapter.LoanerAdapter
import com.codemobile.hackcatonapp.interfaces.QueryUser
import com.codemobile.hackcatonapp.model.LendingModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.text.FieldPosition

class LoanFragment : Fragment() {

    private val moneyAccountArray: ArrayList<String> = arrayListOf("2000", "100", "3000")
    private val loaningArrayList: ArrayList<LendingModel> = arrayListOf()

    private var loaningAdapter: LoanerAdapter? = null
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
        notificationUserMoney()
        setOnAddLoaning()
    }

    private fun setLoan(_view: View) {
        loaningAdapter = LoanerAdapter(context!!, loaningArrayList, 1, object : QueryUser {
            override fun queryUserData(userArrayList: ArrayList<String>, id: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
        rcv_myLoaning.let {
            it.adapter = loaningAdapter
            it.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setOnAddLoaning() {
        btn_toLoadlist.setOnClickListener {
            checkUserSendLoan()
        }
        btnPayment.setOnClickListener {
            deleteUserLoan()
        }
    }

    private fun checkUserSendLoan() {
        if (loaningArrayList.isEmpty()) {
            val intent = Intent(context, LoanListActivity::class.java)
            startActivity(intent)
        } else {
            deleteUserLoan()
        }
    }

    private fun deleteUserLoan() {
        btn_toLoadlist?.text = "Loan"
        loaningArrayList[0].id?.let {
            LeandingRef.document(it).update("userGet", FieldValue.arrayRemove(USER_ID_LOANER))
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
        LeandingRef.whereArrayContains("userGet", USER_ID_LOANER).addSnapshotListener { querySnapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }
            loaningArrayList.clear()
            querySnapshot?.forEach {
                val result = it.toObject(LendingModel::class.java)
                loaningArrayList.add(result)
                loaningArrayList[loaningArrayList.lastIndex].id = it.id
                loaningArrayList[loaningArrayList.lastIndex].lenderName = it.get("lenderName").toString()
            }
//            queryLenderName()
            checkGetLoan(0)
            checkUserLoan()
            loaningAdapter?.notifyDataSetChanged()
        }
    }

    fun notificationUserMoney(){
        //notification money of user
        UserRef.document(USER_ID_LOANER).addSnapshotListener { snapshot, e ->
            if (e != null){
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()){
                moneyAccountArray[0] = snapshot["Money"].toString()
            }
            accountAdapter?.notifyDataSetChanged()
        }
    }

    private fun checkGetLoan(position: Int) {
        if (loaningArrayList.isNotEmpty()) {
            btn_toLoadlist?.text = "Cancel"
            if (loaningArrayList[position].status && loaningArrayList[position].userGet[0]== USER_ID_LOANER){
                btn_toLoadlist?.visibility = View.GONE
            }
        }
    }

    fun init() {
        database = FirebaseFirestore.getInstance()
        LeandingRef = database.collection(LENDER_DATABASE)
        UserRef = database.collection(USER_DATABASE)
    }

    private fun queryLenderName() {
        for (i in 0 until loaningArrayList.size - 1) {
            getLenderName(loaningArrayList[i].lenderName.toString(), i)
        }
    }

    private fun getLenderName(id: String, index: Int) {
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