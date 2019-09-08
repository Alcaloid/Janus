package com.codemobile.hackcatonapp.lendingactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.codemobile.hackcatonapp.*
import com.codemobile.hackcatonapp.adapter.NeedApproveAdapter
import com.codemobile.hackcatonapp.interfaces.UpdateApprove
import com.codemobile.hackcatonapp.model.UserModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_approve.*

class ApproveActivity : AppCompatActivity() {


    lateinit var database: FirebaseFirestore
    lateinit var userRef: CollectionReference
    lateinit var LeandingRef: CollectionReference

    private val userInformation: ArrayList<UserModel> = arrayListOf()
    private var needApproveAdapter: NeedApproveAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_approve)

        val userArray: ArrayList<String> = intent.getStringArrayListExtra(USER_LIST) as ArrayList<String>

        setupFirebase()
        init()
        queryUserInformation(userArray)
        btn_backFromApprove.setOnClickListener {
            finish()
        }
    }

    fun setupFirebase() {
        database = FirebaseFirestore.getInstance()
        userRef = database.collection(USER_DATABASE)
        LeandingRef = database.collection(LENDER_DATABASE)
    }

    fun init() {
        val lending_ID: String = intent.getStringExtra(LENDING_ID) as String
        needApproveAdapter = NeedApproveAdapter(userInformation, object : UpdateApprove {
            override fun updateLending(idUser: String) {
                updateLending(idUser, lending_ID)
            }

        })
        rcv_approve_user_loan.let {
            it.adapter = needApproveAdapter
            it.layoutManager = LinearLayoutManager(this)
        }
    }

    fun updateLending(userID: String, lenderID: String) {
        LeandingRef.document(lenderID).update("status", true)
        LeandingRef.document(lenderID).update("userGet", arrayListOf(userID))
        finish()
    }

    fun queryUserInformation(target: ArrayList<String>) {
        target.forEach { info ->
            userRef.document(info).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Document found in the offline cache
                    val document = task.result
                    val result = document?.toObject(UserModel::class.java) as UserModel
                    userInformation.add(result)
                    userInformation[userInformation.lastIndex].id = document.id
                }
                needApproveAdapter?.notifyDataSetChanged()
            }
        }
    }
}
