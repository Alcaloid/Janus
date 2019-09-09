package com.codemobile.hackcatonapp.lendingactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.codemobile.hackcatonapp.LENDER_DATABASE
import com.codemobile.hackcatonapp.LENDER_MONEY
import com.codemobile.hackcatonapp.USER_DATABASE
import com.codemobile.hackcatonapp.USER_ID_LENDER
import com.codemobile.hackcatonapp.model.LendingModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_lending.*


class AddLendingActivity : AppCompatActivity() {

    lateinit var database: FirebaseFirestore
    lateinit var UserRef: CollectionReference
    lateinit var LeandingRef: CollectionReference
    var lenderMoney: Int = 0
    private var hashMap: HashMap<Any, Any?> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.codemobile.hackcatonapp.R.layout.activity_add_lending)

        init()
        btn_add_card_lending.setOnClickListener {
            if (checkDataEmpty()) {
                if (checkLenderMoney()) {
                    val returnIntent = Intent()
                    returnIntent.putExtra("result", calculateMoneyBalance())
                    setResult(1, returnIntent)
                    addMyLeanderToDatabase(setDataLending())
                } else {
                    messageErrorAddLending()
                }
            } else {
                messageErrorAddLending()
            }
            finish()
        }
        btn_image_backToLending.setOnClickListener {
            finish()
        }
    }

    fun init() {
        database = FirebaseFirestore.getInstance()
        UserRef = database.collection(USER_DATABASE)
        LeandingRef = database.collection(LENDER_DATABASE)
        lenderMoney = intent.getStringExtra(LENDER_MONEY).toInt()
    }

    private fun addMyLeanderToDatabase(userDataSet: LendingModel) {
        val id = LeandingRef.document().getId()
        hashMap = hashMapOf(
            "lender" to USER_ID_LENDER,
            "lenderName" to "Shiota Nagisa",
            "limit" to userDataSet.limit,
            "interest" to userDataSet.interest,
            "period" to userDataSet.period,
            "status" to false,
            "description" to userDataSet.description,
            "userGet" to arrayListOf<String>()
        )
        LeandingRef.document(id).set(hashMap)
        updateMyLeanderInUser(id)
    }

    private fun checkDataEmpty(): Boolean {
        return !(amountEditText.text.isEmpty() && interestEditText.text.isEmpty())
    }

    private fun checkLenderMoney(): Boolean {
        val amountLending = amountEditText.text.toString().toInt()
        return amountLending < lenderMoney
    }

    private fun setDataLending(): LendingModel {
        val limit = amountEditText.text.toString().toInt()
        val interest = interestEditText.text.toString().toInt()
        val period = periodEditText.text.toString()
        val detail = detailEditText.text.toString()
        return LendingModel(limit = limit, interest = interest, period = period, description = detail)
    }

    private fun calculateMoneyBalance(): String {
        val limit = amountEditText.text.toString().toInt()
        return (lenderMoney - limit).toString()
    }

    fun messageErrorAddLending() {
        Toast.makeText(this, "Fail to add lending", Toast.LENGTH_SHORT).show()
    }

    private fun updateMyLeanderInUser(id: String) {
        UserRef.document(USER_ID_LENDER)
            .update("UserLeander", FieldValue.arrayUnion(id))
    }
}
