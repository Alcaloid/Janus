package com.codemobile.hackcatonapp.lendingactivity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.codemobile.hackcatonapp.*
import com.codemobile.hackcatonapp.activity.PaymentActivity
import com.codemobile.hackcatonapp.adapter.NeedApproveAdapter
import com.codemobile.hackcatonapp.interfaces.UpdateApprove
import com.codemobile.hackcatonapp.model.ApiInterface
import com.codemobile.hackcatonapp.model.UserModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_approve.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApproveActivity : AppCompatActivity() {
    private var deeplink: String = "scbeasysim://billpayment-anonymous/1f190d1c-c597-43c6-8065-c7469e66ce6a"
    private var callbackURL: String = "?callback_url=https://easy-loan.com/loan"
    lateinit var database: FirebaseFirestore
    lateinit var userRef: CollectionReference
    lateinit var LeandingRef: CollectionReference
    lateinit var lending_ID: String

    private val userInformation: ArrayList<UserModel> = arrayListOf()
    private var needApproveAdapter: NeedApproveAdapter? = null
    private var limitMoney: Int? = null


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

    private fun queryMoneyOfLender(id: String) {
        LeandingRef.document(lending_ID).get().addOnSuccessListener { document ->
            if (document != null) {
                limitMoney = document["limit"].toString().toInt()
                calculateMoney()
                updateUserLoaner(id)
//                queryLenderData()

            } else {
                Toast.makeText(this, "Fail to get data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun calculateMoney() {
        if (limitMoney != null) {
            var lenderMoney = intent.getStringExtra(LENDER_MONEY).toString().toInt()
            limitMoney?.let { updateLenderMoney(lenderMoney - it) }
        } else {
            Toast.makeText(this, "Fail to get data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateLenderMoney(i: Int) {
        userRef.document(USER_ID_LENDER).update("Money", i)
    }

    fun setupFirebase() {
        database = FirebaseFirestore.getInstance()
        userRef = database.collection(USER_DATABASE)
        LeandingRef = database.collection(LENDER_DATABASE)
    }

    fun init() {
        lending_ID = intent.getStringExtra(LENDING_ID) as String
        needApproveAdapter = NeedApproveAdapter(userInformation, object : UpdateApprove {
            override fun updateLending(idUser: String) {
                updateLending(idUser, lending_ID)
                queryMoneyOfLender(idUser)
                val uri = Uri.parse(deeplink+callbackURL)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                Log.d("deeplink-approve", uri.toString())
                startActivity(intent)
            }

        })
        rcv_approve_user_loan.let {
            it.adapter = needApproveAdapter
            it.layoutManager = LinearLayoutManager(this)
        }
    }

    fun queryLenderData() {
        userRef.document(USER_ID_LENDER).get().addOnSuccessListener { document ->
            if (document != null) {
                val result = document.toObject(UserModel::class.java)
                //data lender
                val lender_accountTo = result?.accountTo
                val lender_authorization = result?.authorization
                val lender_resourceOwnerId = result?.resourceOwnerId
                setMobile(lender_accountTo, lender_authorization, lender_resourceOwnerId)
            } else {
                Toast.makeText(this, "Fail to get data", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun setMobile(account: String?, auth: String?, res: String?) {
        val json: JsonObject = JsonObject()
        json.addProperty("accountTo", account)
        json.addProperty("authorization", auth)
        json.addProperty("resourceOwnerId", res)
        json.addProperty("paymentAmount", limitMoney)
        val call = ApiInterface.getClient().accessToken(json)
        call.enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                println("Fail to Post data" + t)
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                println("deeplink-approve"+response)
                if (response.isSuccessful) {
                    println("Response:" + response.body()!!.get("deeplink_url"))
                    val deeplink = response.body()!!.get("deeplink_url").asString
                    Log.d("deeplink-approve", deeplink)
                    val uri = Uri.parse(deeplink+"?callback_url=https://easy-loan.com/loan")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    Log.d("deeplink-approve", uri.toString())
                    startActivity(intent)
                }
            }

        })
    }

    private fun updateUserLoaner(id: String) {
        //target user
        userRef.document(id).get().addOnSuccessListener { document ->
            if (document != null) {
                var targetMoney = document["Money"].toString().toInt()
                limitMoney?.let {
                    targetMoney = targetMoney + it
                    userRef.document(id).update("Money", targetMoney)
                }
            } else {
                Toast.makeText(this, "Fail to get data", Toast.LENGTH_SHORT).show()
            }
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
