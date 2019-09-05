package com.codemobile.hackcatonapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
<<<<<<< HEAD:app/src/main/java/com/codemobile/hackcatonapp/fragment/LoanFragment.kt
import com.codemobile.hackcatonapp.R
=======
import kotlinx.android.synthetic.main.fragment_loan.*
>>>>>>> AL_Branch:app/src/main/java/com/codemobile/hackcatonapp/LoanFragment.kt

class LoanFragment:Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_loan, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toLoanListBtn.setOnClickListener {
            var intent = Intent(context, LoanListActivity::class.java)
            startActivity(intent)
        }
    }
}