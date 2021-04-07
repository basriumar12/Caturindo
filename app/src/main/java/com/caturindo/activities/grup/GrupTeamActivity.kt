package com.caturindo.activities.grup

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.caturindo.BaseActivity
import com.caturindo.R
import com.caturindo.activities.team.AdapterTeam
import com.caturindo.activities.team.AddTeamActivity
import com.caturindo.activities.team.model.AddTeamRequest
import com.caturindo.activities.team.model.MemberItem
import com.caturindo.activities.team.model.TeamMemberDto
import com.caturindo.constant.Constant
import com.caturindo.models.BaseResponse
import com.caturindo.models.BaseResponseOther
import com.caturindo.preference.Prefuser
import com.caturindo.utils.ApiInterface
import com.caturindo.utils.ServiceGenerator
import com.google.gson.Gson
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_grup_team.*
import kotlinx.android.synthetic.main.activity_team.paren_data_empty
import kotlinx.android.synthetic.main.activity_team.progress_circular
import kotlinx.android.synthetic.main.custom_toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GrupTeamActivity : BaseActivity(),AdapterTeam.OnListener {
    private var toolbar: Toolbar? = null
    private var mTitle: TextView? = null
    private var mNavigationMenu: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grup_team)

        toolbar = findViewById(R.id.toolbar)
        mTitle = findViewById(R.id.tv_toolbar_title)
        setSupportActionBar(toolbar)
        mTitle?.setText(intent.getStringExtra("NAMA"))
        setupNavigationMenu()



    }

    override fun onResume() {
        super.onResume()
        getTeamMember()
    }

    private fun getTeamMember() {
        val api = ServiceGenerator.createService(
                ApiInterface::class.java,
                Constant.USERNAME,
                Constant.PASS
        )

        progress_circular.visibility = View.VISIBLE
        api.getTeamMemberWithGrup(Prefuser().getUser()?.id, intent.getStringExtra("ID")).enqueue(object : Callback<BaseResponse<TeamMemberDto>> {
            override fun onFailure(call: Call<BaseResponse<TeamMemberDto>>, t: Throwable) {
                showLongErrorMessage("Gagal dapatkan data member, ada kesalahan akses ke server")

                progress_circular.visibility = View.GONE
            }

            override fun onResponse(call: Call<BaseResponse<TeamMemberDto>>, response: Response<BaseResponse<TeamMemberDto>>) {

                progress_circular.visibility = View.GONE
                if (response.isSuccessful) {
                    if (response.body()?.status == true) {

                        onSuccessGetTeam(response.body()?.data?.member as List<MemberItem>)
                    }
                } else {
                    showLongErrorMessage("Gagal dapatkan data member")
                }
            }
        })
    }

    fun onSuccessGetTeam(data: List<MemberItem>) {

       val adapterTeam = AdapterTeam(this, data as MutableList<MemberItem> , this)
        rv_grup_team.layoutManager = LinearLayoutManager(this)
        rv_grup_team.adapter = adapterTeam
        adapterTeam?.notifyDataSetChanged()
       // rv_grup_team.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))


        if (data.isNullOrEmpty()) {
            paren_data_empty.visibility = View.VISIBLE
        } else {

            paren_data_empty.visibility = View.GONE
        }
    }
    private fun setupNavigationMenu() {

        mNavigationMenu = findViewById(R.id.img_toolbar_start_button)
        mNavigationMenu?.setImageResource(R.drawable.ic_baseline_chevron_left_24)
        mNavigationMenu?.setOnClickListener(View.OnClickListener { finish() })

        img_first_option.visibility = View.GONE
        img_second_option.setImageResource(R.drawable.ic_baseline_more_vert_24)
        img_second_option.setOnClickListener {
            startActivity(Intent(this,AddTeamActivity::class.java)
                    .putExtra("ID",intent.getStringExtra("ID"))
            )
        }
    }

    override fun onClick(data: MemberItem) {
        val intent = Intent(Intent.ACTION_CALL);
        intent.data = Uri.parse("tel:${data.phone}")
        if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
        ) {

            persmission()
        }

        startActivity(intent)
    }

    override fun onDelete(data: MemberItem) {
        if (Prefuser().getUser()?.role.equals("3")) {
            showLongErrorMessage("Anda tidak mempunyai akses")
        } else {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Konfirmasi")
            builder.setMessage("Apakah anda yakin menghapus team?")
            builder.setPositiveButton("Ya") { dialogInterface, i -> // clear data
               deleteTeam(AddTeamRequest(data.idMember, Prefuser().getUser()?.id, intent.getStringExtra("ID")))
            }
            builder.setNegativeButton("tidak", null)
            builder.show()
        }
    }

    fun deleteTeam(data: AddTeamRequest) {
        val api = ServiceGenerator.createService(
                ApiInterface::class.java,
                Constant.USERNAME,
                Constant.PASS
        )
        progress_circular.visibility = View.VISIBLE
        api.postDeleteTeam(data).enqueue(object  : Callback<BaseResponseOther>{
            override fun onFailure(call: Call<BaseResponseOther>, t: Throwable) {

                progress_circular.visibility = View.GONE
                showLongErrorMessage("Gagal hapus data member, ada kesalahan jaringan atau akses ke server")
            }

            override fun onResponse(call: Call<BaseResponseOther>, response: Response<BaseResponseOther>) {
                getTeamMember()
                progress_circular.visibility = View.GONE
                Log.e("TAG","data ${Gson().toJson(response?.body()?.status)}")
                if (response.isSuccessful) {
                    if (response.body()?.status == true) {
                        showLongSuccessMessage(response.body()?.message.toString())
                    } else {
                        showLongErrorMessage(response.body()?.message.toString())
                    }
                }
            }
        })

    }

    private fun persmission() {
        Dexter.withActivity(this).withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.ACCESS_FINE_LOCATION
        )
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        report?.let {
                            if (it.areAllPermissionsGranted()) {
                            }
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                            permissions: MutableList<PermissionRequest>?,
                            token: PermissionToken?
                    ) {
                        token?.continuePermissionRequest()
                    }
                }).check()
    }

}