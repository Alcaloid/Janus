package com.codemobile.hackcatonapp.lendingactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codemobile.hackcatonapp.R
import kotlinx.android.synthetic.main.activity_approve.*

class ApproveActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_approve)

        btn_backFromApprove.setOnClickListener {
            finish()
        }
    }
}
