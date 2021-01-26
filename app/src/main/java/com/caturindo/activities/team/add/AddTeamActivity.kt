package com.caturindo.activities.team.add

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caturindo.BaseActivity
import com.caturindo.R
import com.caturindo.adapters.AddTeamItemAdapter
import com.caturindo.models.UserDto
import com.caturindo.models.UserModel
import kotlinx.android.synthetic.main.activity_add_team.*
import java.util.*

class AddTeamActivity : BaseActivity(), AddTeamContract.View {
    private var toolbar: Toolbar? = null
    private var mTitle: TextView? = null
    private var mNavigationMenu: ImageView? = null
    private var rvMember: RecyclerView? = null
    private var adapter: AddTeamItemAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_team)
        setupToolbar()
        setupMemberList()
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        mTitle = findViewById(R.id.tv_toolbar_title)
        setSupportActionBar(toolbar)
        mTitle?.setText("Add Team")
        setupNavigationMenu()
    }

    private fun setupNavigationMenu() {
        mNavigationMenu = findViewById(R.id.img_toolbar_start_button)
        mNavigationMenu?.setImageResource(R.drawable.ic_baseline_chevron_left_24)
        mNavigationMenu?.setOnClickListener(View.OnClickListener { finish() })
    }

    private fun setupMemberList() {
        val members: ArrayList<UserModel?> = ArrayList<UserModel?>()
        members.add(UserModel())
        members.add(UserModel())
        members.add(UserModel())
        members.add(UserModel())
        rvMember = findViewById(R.id.rv_member)
        adapter = AddTeamItemAdapter(this, members)
        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvMember?.setLayoutManager(manager)
        rvMember?.setAdapter(adapter)
    }

    override fun showProgress() {
        progress_circular.visibility = View.VISIBLE
    }

    override fun hideProgress() {

        progress_circular.visibility = View.GONE
    }

    override fun onSuccessGet(data: List<UserDto>) {
        
    }

    override fun onErrorGetData(msg: String?) {
        
    }
}