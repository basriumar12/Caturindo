package com.caturindo.activities.team.add

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caturindo.BaseActivity
import com.caturindo.R
import com.caturindo.models.UserDto
import com.caturindo.preference.Prefuser
import com.caturindo.utils.SwipeToDeleteCallback
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_add_team.*
import kotlinx.android.synthetic.main.fragment_search_bar.*

class AddTeamMeetingActivity : BaseActivity(), AddTeamContract.View, AdapterAddTeam.OnListener {
    private var toolbar: Toolbar? = null
    private var mTitle: TextView? = null
    private var mNavigationMenu: ImageView? = null
    private var rvMember: RecyclerView? = null
    private var adapter: AdapterAddTeam? = null
    private var adapterList: AdapterListAddTeam? = null
    private var dataUserDto: MutableList<UserDto> = ArrayList()
    private lateinit var recyclerViewteam: RecyclerView
    private var presenter: AddTeamPresenter = AddTeamPresenter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_team)
        setupToolbar()
        initData()
        initView()

    }

    private fun initData() {
        presenter = AddTeamPresenter(this)
        presenter.getUser()

        if (!Prefuser().getAddTeam().isNullOrEmpty()){
            Prefuser().getAddTeam()?.let { dataUserDto.addAll(it) }
            parent_list_add.visibility = View.VISIBLE
        }

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
        mNavigationMenu?.setOnClickListener(View.OnClickListener {
            saveBack()
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        saveBack()
    }

    fun saveBack() {
        if (!dataUserDto.isNullOrEmpty())
            Prefuser().setAddTeam(dataUserDto as ArrayList<UserDto>)
        finish()
    }

    fun customDialog() {
        val dialogView = layoutInflater.inflate(R.layout.custom_dialog_list_team, null)
        var dialog = AlertDialog.Builder(this)
        var customDialog = dialog?.create()
        customDialog?.setView(dialogView)
        customDialog?.show()
        customDialog?.setCancelable(false)

        recyclerViewteam = dialogView.findViewById(R.id.rv_team)
        recyclerViewteam.layoutManager = LinearLayoutManager(this)
        adapterList = AdapterListAddTeam(this, dataUserDto)
        recyclerViewteam.adapter = adapterList
        adapterList?.notifyDataSetChanged()
        ///remove
        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = recyclerViewteam.adapter as AdapterListAddTeam
                adapter.removeAt(viewHolder.adapterPosition)
                dataUserDto.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerViewteam)

        val btnOk = dialogView.findViewById<Button>(R.id.btn_ok)
        btnOk.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }

    override fun showProgress() {
        progress_circular.visibility = View.VISIBLE
    }

    override fun hideProgress() {

        progress_circular.visibility = View.GONE
    }

    override fun onSuccessGet(data: List<UserDto>) {

        rv_member?.layoutManager = LinearLayoutManager(this)
        adapter = AdapterAddTeam(this, data as MutableList<UserDto>, this)
        rv_member?.adapter = adapter
        adapter?.notifyDataSetChanged()

        search_text.setOnQueryTextListener(object :
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter?.filter?.filter(newText)
                return false
            }

        })
    }

    override fun onErrorGetData(msg: String?) {
        showLongErrorMessage(msg)
    }

    override fun onClick(data: UserDto) {

        var addValidate = false
        dataUserDto.forEach {
            if (it.id.equals(data.id)) {
                addValidate = true
                showInfoMessage("Staf sudah di tambahkan")
            } else {
                addValidate = false
            }
        }

        if (addValidate.equals(false)) {
            parent_list_add.visibility = View.VISIBLE
            dataUserDto.add(data)
            adapterList?.notifyDataSetChanged()
         }


    }

    private fun initView() {
        recyclerViewteam = findViewById(R.id.rv_team)
        recyclerViewteam.layoutManager = LinearLayoutManager(this)
        adapterList = AdapterListAddTeam(this, dataUserDto)
        recyclerViewteam.adapter = adapterList
        adapterList?.notifyDataSetChanged()
        ///remove
        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = recyclerViewteam.adapter as AdapterListAddTeam
                adapter.removeAt(viewHolder.adapterPosition)
                dataUserDto.removeAt(viewHolder.adapterPosition)
                if (dataUserDto.isNullOrEmpty()) {
                    parent_list_add.visibility = View.GONE
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerViewteam)
    }


}