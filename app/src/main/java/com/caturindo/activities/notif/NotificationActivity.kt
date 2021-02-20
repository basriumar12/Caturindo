package com.caturindo.activities.notif

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caturindo.BaseActivity
import com.caturindo.R
import com.caturindo.activities.login.LoginActivity
import com.caturindo.activities.notif.model.NotifDto
import com.caturindo.adapters.NotificationItemAdapter
import com.caturindo.preference.Prefuser
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.custom_toolbar.*

class NotificationActivity : BaseActivity(), NotifContract.View, AdapterNotif.OnListener {

    lateinit var presenter: NotifPresenter
    private var toolbar: Toolbar? = null
    private var mTitle: TextView? = null
    private var mNavigationMenu: ImageView? = null
    private var rvNotification: RecyclerView? = null
    private val adapter: NotificationItemAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        if (Prefuser().getUser() == null){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        presenter = NotifPresenter(this)
        presenter.getNotif(Prefuser().getUser()?.id.toString())
        setupToolbar()
        setupRecyclerView()


    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        mTitle = findViewById(R.id.tv_toolbar_title)
        setSupportActionBar(toolbar)
        mTitle?.setText("Notification")
        setupNavigationMenu()
        img_first_option.visibility = View.GONE
        img_second_option.visibility = View.GONE
    }

    private fun setupNavigationMenu() {
        mNavigationMenu = findViewById(R.id.img_toolbar_start_button)
        mNavigationMenu?.setImageResource(R.drawable.ic_baseline_chevron_left_24)
        mNavigationMenu?.setOnClickListener(View.OnClickListener { finish() })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun setupRecyclerView() {
        rvNotification = findViewById(R.id.rv_notification)
    }

    override fun showProgress() {
        progress_circular.visibility = View.VISIBLE

    }

    override fun hideProgress() {

        progress_circular.visibility = View.GONE
    }

    override fun successData(data: ArrayList<NotifDto>) {
        val adapterNotif = AdapterNotif(this, data, this)
        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvNotification?.setLayoutManager(manager)
        val myDivider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        myDivider.setDrawable(ContextCompat.getDrawable(this, R.drawable.custom_divider)!!)
        rvNotification?.addItemDecoration(myDivider)
        rvNotification?.setAdapter(adapterNotif)

        if (data.isNullOrEmpty()) {
            paren_data_empty.visibility = View.VISIBLE
        } else {
            paren_data_empty.visibility = View.GONE
        }
    }

    override fun failt(msg: String) {
        showLongErrorMessage(msg)
    }

    override fun onClick(data: NotifDto) {

    }

    override fun dataEmpty() {
        paren_data_empty.visibility = View.VISIBLE
    }
}