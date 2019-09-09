package com.codemobile.hackcatonapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codemobile.hackcatonapp.R
import com.codemobile.hackcatonapp.USER_ID_LENDER
import com.codemobile.hackcatonapp.USER_ID_LOANER
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val _view = inflater.inflate(R.layout.fragment_home, container, false)

            if (USER_ID_LENDER == "1" && USER_ID_LOANER == "1") {
                _view.profileimg.setImageResource(R.drawable.profile01)
            }else if (USER_ID_LENDER == "2" && USER_ID_LOANER == "2"){
                _view.profileimg.setImageResource(R.drawable.profile02)
            }
        return _view
    }
}