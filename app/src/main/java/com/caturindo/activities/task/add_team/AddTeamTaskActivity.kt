package com.caturindo.activities.task.add_team

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caturindo.BaseActivity
import com.caturindo.R
import com.caturindo.activities.task.add_team.model.AddMemberTaskDto
import com.caturindo.activities.team.add.AdapterAddTeam
import com.caturindo.activities.team.add.AdapterListAddTeam
import com.caturindo.activities.team.add.AddTeamPresenter
import com.caturindo.models.UserDto
import com.caturindo.preference.ModelUserTask
import com.caturindo.preference.Prefuser
import kotlinx.android.synthetic.main.activity_add_team.*
import kotlinx.android.synthetic.main.activity_add_team_tasl.*
import kotlinx.android.synthetic.main.activity_add_team_tasl.progress_circular
import kotlinx.android.synthetic.main.activity_add_team_tasl.rv_member
import kotlinx.android.synthetic.main.fragment_search_bar.*

class AddTeamTaskActivity : BaseActivity(), AddTeamTaskContract.View, AdapterAddTeamTask.OnListener {
    val datas : ArrayList<AddMemberTaskDto> = ArrayList()
    private var toolbar: Toolbar? = null
    private var mTitle: TextView? = null
    private var mNavigationMenu: ImageView? = null
    private var rvMember: RecyclerView? = null
    private var adapter: AdapterAddTeamTask? = null
    private var adapterList: AdapterListAddTeam? = null
    private var dataUserDto: MutableList<UserDto> = ArrayList()
    private lateinit var recyclerViewteam: RecyclerView
    private var presenter: AddTeamTaskPresenter = AddTeamTaskPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_team_tasl)

        presenter = AddTeamTaskPresenter(this)
        presenter.getUser()
        setupNavigationMenu()
        setupToolbar()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        saveDataTeam()
    }

    fun saveDataTeam(){
        if (!datas.isNullOrEmpty())
            Prefuser().setTeamTask(datas)

        finish()
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
            saveDataTeam()
        })
    }

    override fun showProgress() {
        progress_circular.visibility = View.VISIBLE
    }

    override fun hideProgress() {

        progress_circular.visibility = View.GONE
    }

    override fun onSuccessGet(data: List<UserDto>) {
        rv_member?.layoutManager = LinearLayoutManager(this)
        adapter = AdapterAddTeamTask(this, data as MutableList<UserDto>, this)
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

    override fun suksesAddMember(msg: String, data: AddMemberTaskDto) {
        showSuccessMessage(msg)

        datas.add(data)


    }

    override fun faildAddMember(msg: String) {
        showLongErrorMessage(msg)
    }

    override fun onClick(data: UserDto) {
        presenter.addTeam(Prefuser().getUser()?.id.toString(), intent.getStringExtra("ID"))
    }
}