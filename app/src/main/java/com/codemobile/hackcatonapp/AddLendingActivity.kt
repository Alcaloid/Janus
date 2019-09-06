package com.codemobile.hackcatonapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codemobile.hackcatonapp.model.LeandingModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_lending.*

class AddLendingActivity : AppCompatActivity() {

    lateinit var database: FirebaseFirestore
    lateinit var UserRef: CollectionReference
    lateinit var LeandingRef: CollectionReference
    private var hashMap: HashMap<Any, Any> = HashMap()

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
            "Lender" to USER_ID,
            "Limit"  to 1000,
            "Interest" to 3,
            "Period" to "10 Year",
            "Status" to false,
            "UserGet" to arrayListOf(null)
        )
        LeandingRef.document(id).set(hashMap)
        updateMyLeanderInUser(id)
    }

    fun updateMyLeanderInUser(id: String) {
        UserRef.document("0")
            .update("UserLeander", FieldValue.arrayUnion(id))
    }
}
