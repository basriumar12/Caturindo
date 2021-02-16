package com.caturindo.activities

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caturindo.R
import com.caturindo.activities.login.LoginActivity
import com.caturindo.activities.meeting.MeetingActivity
import com.caturindo.activities.team.TeamActivity
import com.caturindo.adapters.HomeItemAdapter
import com.caturindo.constant.Constant
import com.caturindo.models.BaseResponseOther
import com.caturindo.models.HomeItemModel
import com.caturindo.preference.Prefuser
import com.caturindo.utils.ApiInterface
import com.caturindo.utils.AppConstant
import com.caturindo.utils.ServiceGenerator
import com.google.gson.Gson
import com.orhanobut.hawk.Hawk
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class HomeActivity : AppCompatActivity(), HomeItemAdapter.ItemListener {
    private val self = this
    private var adapter: HomeItemAdapter? = null
    private var recyclerView: RecyclerView? = null
    private var arraylist: ArrayList<HomeItemModel>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.drawable.caturindo_32)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorPrimaryDark)))
        supportActionBar?.title = " PT. Caturindo"
        recyclerView = findViewById<View>(R.id.rvHome) as RecyclerView
        arraylist = ArrayList()
        arraylist?.add(HomeItemModel(1, "Meeting", R.drawable.ic_menu_meeting))
        arraylist?.add(HomeItemModel(2, "Team", R.drawable.ic_menu_team))
        arraylist?.add(HomeItemModel(4, "KPI", R.drawable.ic_icon_kpi))
        arraylist?.add(HomeItemModel(5, "Productivity Achievement", R.drawable.ic_icon_productivity))
        arraylist?.add(HomeItemModel(6, "Code of Conduct", R.drawable.ic_icon_code_of_conduct))
        arraylist?.add(HomeItemModel(7, "Job", R.drawable.ic_icon_job))
        arraylist?.add(HomeItemModel(8, "MTN/OEE", R.drawable.ic_icon_mtn_oee))
        arraylist?.add(HomeItemModel(9, "Performance for Customer", R.drawable.ic_icon_performance_of_customer))
        arraylist?.add(HomeItemModel(10, "PPIC", R.drawable.ic_icon_ppic))
        arraylist?.add(HomeItemModel(11, "New Project", R.drawable.ic_icon_new_project))
        adapter = HomeItemAdapter(this, arraylist, this)
        recyclerView?.adapter = adapter
        val manager = GridLayoutManager(this, 3, RecyclerView.VERTICAL, false)
        recyclerView?.layoutManager = manager
        Log.e("TAG", "cek user pref" + Gson().toJson(Prefuser().getUser()))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                val builder = AlertDialog.Builder(self)
                builder.setTitle("Logout Confirmation")
                builder.setMessage("Are you sure want to logout?")
                builder.setPositiveButton("Logout") { dialogInterface, i -> // clear data
                    Hawk.deleteAll()
                    Prefuser().setUser(null)
                    logout()
                    val sessions = self.getSharedPreferences(AppConstant.SHARED_PREFERENCE_NAME, 0)
                    val edit = sessions.edit()
                    edit.clear()
                    edit.commit()
                    val intent = Intent(self, LoginActivity::class.java)
                    startActivity(intent)
                    self.finish()
                    Prefuser().setUser(null)
                }
                builder.setNegativeButton("Cancel", null)
                builder.show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onItemClick(item: HomeItemModel) {
        when (item.id) {
            1 -> {
                val intent = Intent(self, MeetingActivity::class.java)
                startActivity(intent)
            }
            2 -> {
                val intentTeam = Intent(self, TeamActivity::class.java)
                startActivity(intentTeam)
            }
            3 -> {
                val builder = AlertDialog.Builder(self)
                builder.setTitle("Logout Confirmation")
                builder.setMessage("Are you sure want to logout?")
                builder.setPositiveButton("Logout") { dialogInterface, i -> // clear data
                    Prefuser().setUser(null)
                    Hawk.deleteAll()
                    val sessions = self.getSharedPreferences(AppConstant.SHARED_PREFERENCE_NAME, 0)
                    val edit = sessions.edit()
                    edit.clear()
                    edit.commit()
                    val intent = Intent(self, LoginActivity::class.java)
                    startActivity(intent)
                    self.finish()
                    logout()
                }
                builder.setNegativeButton("Cancel", null)
                builder.show()
            }
        }
    }

    private fun logout() {

        val api = ServiceGenerator.createService(
                ApiInterface::class.java,
                Constant.USERNAME,
                Constant.PASS
        )

        api.postLogout(Prefuser().getUser()?.id.toString()).enqueue(object : Callback<BaseResponseOther>{
            override fun onFailure(call: Call<BaseResponseOther>, t: Throwable) {
                Log.e("TAG","errro ${t.message}")
            }

            override fun onResponse(call: Call<BaseResponseOther>, response: Response<BaseResponseOther>) {
                Log.e("TAG","suskses ${response.body()?.status}")

            }
        })
    }
}