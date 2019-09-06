package com.codemobile.hackcatonapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.codemobile.hackcatonapp.AddLendingActivity
import com.codemobile.hackcatonapp.R
import com.codemobile.hackcatonapp.RESULT_CODE_ADD_LENDING
import com.codemobile.hackcatonapp.adapter.AccountAdapter
import com.codemobile.hackcatonapp.adapter.LeandingAdapter
import com.codemobile.hackcatonapp.database.AppDatabase
import com.codemobile.hackcatonapp.database.workerThread
import com.codemobile.hackcatonapp.model.LeandingModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_lend.*

class LendingFragment : Fragment() {

    private val moneyAccountArray: ArrayList<String> = arrayListOf("100000", "2000", "10000")
    private val lendingArrayList: ArrayList<LeandingModel> = arrayListOf()
    private var appDatabase: AppDatabase? = null
    var mCMWorkerThread: workerThread = workerThread("favoritedatabase").also {
        it.start()
    }
    private var leandingAdapter: LeandingAdapter? = null
    private var accountAdapter: AccountAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)
        loadLending()
        setAccount(view)
        setLending(view)
        setOnAddLending()
    }

    private fun init(view: View) {
        appDatabase = AppDatabase.getInstance(view.context).also {
            it.openHelper.readableDatabase
        }
    }

    private fun setLending(_view: View) {
        leandingAdapter =
            LeandingAdapter(lendingArrayList)
        rcv_myLending.let {
            it.adapter = leandingAdapter
            it.layoutManager = LinearLayoutManager(_view.context)
        }
    }

    private fun setOnAddLending() {
        if (lendingArrayList.isEmpty()) {
            image_notLeanding.visibility = View.VISIBLE
            txt_notLeanding.visibility = View.VISIBLE
        }
        leandingAdapter?.notifyDataSetChanged()
        btn_addLending.setOnClickListener {
            //go to xxxx
            startActivityForResult(Intent(context,AddLendingActivity::class.java),1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1){
            if (resultCode == RESULT_CODE_ADD_LENDING){
                val result = data?.getSerializableExtra("id") as LeandingModel
                lendingArrayList.add(result)
                if (lendingArrayList.isNotEmpty()){
                    image_notLeanding.visibility = View.GONE
                    txt_notLeanding.visibility = View.GONE
                }
                leandingAdapter?.notifyDataSetChanged()
            }
        }
    }

    private fun setAccount(_view: View) {
        accountAdapter =
            AccountAdapter(moneyAccountArray)
        rcv_account.let {
            it.adapter = accountAdapter
            it.layoutManager = LinearLayoutManager(_view.context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    fun loadLending(){
        val task = Runnable {
            val result = appDatabase?.favoriteDao()?.queryFavorites()
            val gson = Gson()
            val json = gson.toJson(result)
            val data =
                gson.fromJson<List<LeandingModel>>(
                    json,
                    object : TypeToken<List<LeandingModel>>() {}.type
                )
            lendingArrayList.addAll(data)
        }
        mCMWorkerThread.postTask(task)
    }
}