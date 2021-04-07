package com.caturindo.activities.grup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.caturindo.BaseActivity
import com.caturindo.R
import com.caturindo.activities.task.CreateTaskPresenter
import kotlinx.android.synthetic.main.activity_grup.*
import kotlinx.android.synthetic.main.custom_toolbar.*

class AddGrupActivity : BaseActivity(), CreatingGrupContract.View {
    private var toolbar: Toolbar? = null
    private var mTitle: TextView? = null
    private var mNavigationMenu: ImageView? = null
    lateinit var presenter: CreateGrupPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grup)
        toolbar = findViewById(R.id.toolbar)
        mTitle = findViewById(R.id.tv_toolbar_title)
        setSupportActionBar(toolbar)
        mTitle?.setText("Create Group")
        setupNavigationMenu()

        img_first_option.visibility = View.GONE
        img_second_option.visibility = View.GONE
        presenter= CreateGrupPresenter(this)

        btn_save.setOnClickListener {
            
            if (et_name_group.text.toString().isNullOrEmpty()){
                showLongErrorMessage("Nama grup tidak bisa kosong")
            }else{
                presenter.postCreate(et_name_group.text.toString())
            }
        }
    }
    private fun setupNavigationMenu() {
        mNavigationMenu = findViewById(R.id.img_toolbar_start_button)
        mNavigationMenu?.setImageResource(R.drawable.ic_baseline_chevron_left_24)
        mNavigationMenu?.setOnClickListener(View.OnClickListener { finish() })
    }
    override fun showProgress() {
        progress_circular.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress_circular.visibility = View.GONE
    }

    override fun succesCreate(msg: String) {

        showLongSuccessMessage(msg)
        finish()
    }

    override fun failCreate(msg: String) {
        showLongErrorMessage(msg)
        
    }
}