package com.codemobile.hackcatonapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codemobile.hackcatonapp.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.custom_card_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    lateinit var database: FirebaseFirestore
    lateinit var UserRef: CollectionReference
    var money: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        notificationUserMoney()

        if (USER_ID_LENDER == "1" && USER_ID_LOANER == "1") {
            view.nametxt.setText("Peter Parker")
            view.profileimg.setImageResource(R.drawable.profile01)
        } else if (USER_ID_LENDER == "2" && USER_ID_LOANER == "2") {
            view.nametxt.setText("Peter Parker")
            view.profileimg.setImageResource(R.drawable.profile02)
        }
    }

    fun notificationUserMoney() {
        //notification money of user
        UserRef.document(USER_ID_LOANER).addSnapshotListener { snapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                money = snapshot["Money"].toString()
            }
            balance?.text = money
        }
    }

    fun init() {
        database = FirebaseFirestore.getInstance()
        UserRef = database.collection(USER_DATABASE)
    }
}