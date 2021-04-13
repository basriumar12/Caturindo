package com.caturindo.activities.meeting.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.caturindo.BaseActivity
import com.caturindo.R
import com.caturindo.activities.meeting.create.AdapterFileUpload
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_create_meeting.*
import kotlinx.android.synthetic.main.activity_list_file.*
import kotlinx.android.synthetic.main.custom_toolbar.*

class ListFileActivity : BaseActivity() {
    private var toolbar: Toolbar? = null
    private var mTitle: TextView? = null
    private var mNavigationMenu: ImageView? = null
    private var urlImage: ArrayList<String> = ArrayList()
    private var adapterImageUpload = AdapterListFileUpload(urlImage as ArrayList<String>, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_file)
        setupToolbar()
        val data = intent.getStringArrayListExtra("IMAGE")
        urlImage = data
        Log.e("TAG","get data list file ${Gson().toJson(urlImage)}")
        rv_file.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterImageUpload = AdapterListFileUpload(urlImage as ArrayList<String>, this)
        rv_file.adapter = adapterImageUpload
        adapterImageUpload.notifyDataSetChanged()

    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        mTitle = findViewById(R.id.tv_toolbar_title)
        setSupportActionBar(toolbar)
        mTitle?.text = "List file attachment"
        setupNavigationMenu()

        img_first_option.visibility = View.GONE
        img_second_option.visibility = View.GONE
    }

    private fun setupNavigationMenu() {
        mNavigationMenu = findViewById(R.id.img_toolbar_start_button)
        mNavigationMenu?.setImageResource(R.drawable.ic_baseline_chevron_left_24)
        mNavigationMenu?.setOnClickListener(View.OnClickListener { finish() })
    }
}