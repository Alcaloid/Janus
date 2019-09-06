package com.codemobile.hackcatonapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codemobile.hackcatonapp.model.LeandingModel
import kotlinx.android.synthetic.main.activity_add_lending.*

class AddLendingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lending)

        btn_add_card_lending.setOnClickListener {
            var intent:Intent = Intent()
            intent.putExtra("id", LeandingModel(100000, 1, "3 mouth", "Wating"))
            setResult(RESULT_CODE_ADD_LENDING,intent)
            finish()
//            Intent intent = new Intent();
//            intent.putExtra("id","value")
//            setResult(RESULT_OK, intent);
//            finish();
        }
    }
}
