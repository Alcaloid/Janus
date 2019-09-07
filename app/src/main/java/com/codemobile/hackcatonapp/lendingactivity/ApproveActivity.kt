package com.codemobile.hackcatonapp.lendingactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codemobile.hackcatonapp.R
import com.codemobile.hackcatonapp.USER_LIST
import kotlinx.android.synthetic.main.activity_approve.*

class ApproveActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_approve)

        var userArray:ArrayList<String> = intent.getStringArrayListExtra(USER_LIST) as ArrayList<String>

        btn_backFromApprove.setOnClickListener {
            finish()
        }
    }
}
