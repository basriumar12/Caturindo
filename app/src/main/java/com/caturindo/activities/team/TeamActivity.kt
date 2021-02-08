package com.caturindo.activities.team

import android.Manifest
import android.companion.BluetoothDeviceFilter
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.caturindo.BaseActivity
import com.caturindo.R
import com.caturindo.activities.team.add.AddTeamMeetingActivity
import com.caturindo.activities.team.edit.EditUserActivity
import com.caturindo.activities.team.model.MemberItem
import com.caturindo.adapters.TeamItemAdapter
import com.caturindo.models.UserDto
import com.caturindo.models.UserDtoNew
import com.caturindo.preference.Prefuser
import com.google.gson.Gson
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_team.*

class TeamActivity : BaseActivity(), TeamContract.View, AdapterTeam.OnListener {
    private var menuMore: ImageView? = null
    private var menuList: ImageView? = null
    private var userAvatar: ImageView? = null
    private var rvTeams: RecyclerView? = null
    private var adapter: TeamItemAdapter? = null
    private lateinit var presenter: TeamPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)
        presenter = TeamPresenter(this)


        bindView()
        setupButtonAction()
        persmission()


    }

    override fun onResume() {
        super.onResume()
        presenter.getUser(Prefuser().getUser()?.id.toString())
        presenter.getTeamMember(Prefuser().getUser()?.id.toString())
        presenter.getUser()
    }

    private fun bindView() {

        menuList = findViewById(R.id.img_menu)
        menuMore = findViewById(R.id.img_more)
    }

    private fun setupButtonAction() {
        menuList?.setOnClickListener { finish() }
        menuMore?.setOnClickListener { startActivity(Intent(this@TeamActivity, AddTeamActivity::class.java)) }
        img_edit?.setOnClickListener { startActivity(Intent(this@TeamActivity, EditUserActivity::class.java)) }


    }


    override fun showProgress() {

        progress_circular.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress_circular.visibility = View.GONE
    }

    override fun onSuccessGetDetail(data: UserDtoNew) {
        Glide.with(this)
                .load(data.imageProfile)
                .error(R.drawable.no_imge)
                .apply(RequestOptions().circleCrop())
                .into(profile_image)
        Glide.with(this)
                .load(data.imageBackground)
                .into(img_background)

        var name = ""
        if (data.name.isNullOrEmpty()) {
            name = ""
        } else {
            name = data.name
        }
        tv_username.text = name
        tv_usertag.text = data.username
        var role = ""
        if (data.role?.toString().equals("2")) {
            role = "Manager"
        } else if (data.role.equals("3")) {
            role = "Staf"
        } else {
            role = ""
        }

        tv_user_level.text = role
        tv_user_email.text = data.email

        if (data.phone != null ||
                data.phone.equals("null")) {
            tv_user_phone.text = data.phone.toString()
        }


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

    override fun onErrorGetDataDetail(msg: String?) {
        showLongErrorMessage(msg)
    }


    override fun onSuccessGet(data: List<UserDto>) {


    }

    override fun onSuccessAddTeam(msg: String?) {

    }

    override fun onSuccessGetTeam(data: List<MemberItem>) {
        Log.e("TAG","team member ${Gson().toJson(data)}")
        val adapterTeam = AdapterTeam(this, data as MutableList<MemberItem>, this)
        rv_team.layoutManager = LinearLayoutManager(this)
        rv_team.adapter = adapterTeam
        adapterTeam.notifyDataSetChanged()
        rv_team.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))


        if (data.isNullOrEmpty()) {
            paren_data_empty.visibility = View.VISIBLE
        } else {

            paren_data_empty.visibility = View.GONE
        }
    }

    override fun onErrorGetData(msg: String?) {
        showLongErrorMessage(msg)
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
}