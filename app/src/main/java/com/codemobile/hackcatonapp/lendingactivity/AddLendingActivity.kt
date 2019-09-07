package com.codemobile.hackcatonapp.lendingactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codemobile.hackcatonapp.R
import com.codemobile.hackcatonapp.USER_ID_LENDER
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_lending.*

class AddLendingActivity : AppCompatActivity() {

    lateinit var database: FirebaseFirestore
    lateinit var UserRef: CollectionReference
    lateinit var LeandingRef: CollectionReference
    private var hashMap: HashMap<Any, Any?> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lending)

        init()
        btn_add_card_lending.setOnClickListener {
//            addMyLeanderToDatabase()
            finish()
        }
    }

    fun init() {
        database = FirebaseFirestore.getInstance()
        UserRef = database.collection("User")
        LeandingRef = database.collection("Leanding")
    }

    fun addMyLeanderToDatabase() {
        val id = LeandingRef.document().getId()
        hashMap = hashMapOf(
            "lender" to USER_ID_LENDER,
            "limit" to 1000,
            "interest" to 3,
            "period" to "10 Year",
            "status" to false,
            "description" to "Example",
            "userGet" to arrayListOf<String>()
        )
        LeandingRef.document(id).set(hashMap)
        updateMyLeanderInUser(id)
    }

    fun updateMyLeanderInUser(id: String) {
        UserRef.document(USER_ID_LENDER)
            .update("UserLeander", FieldValue.arrayUnion(id))
    }
}
