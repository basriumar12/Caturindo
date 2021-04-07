package com.caturindo.activities.team

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.caturindo.BaseActivity
import com.caturindo.R
import com.caturindo.activities.grup.AddGrupActivity
import com.caturindo.activities.grup.GrupTeamActivity
import com.caturindo.activities.grup.model.ResponseGroupDto
import com.caturindo.activities.team.edit.EditUserActivity
import com.caturindo.activities.team.model.AddTeamRequest
import com.caturindo.activities.team.model.MemberItem
import com.caturindo.adapters.TeamItemAdapter
import com.caturindo.constant.Constant
import com.caturindo.models.BaseResponse
import com.caturindo.models.BaseResponseOther
import com.caturindo.models.UserDto
import com.caturindo.models.UserDtoNew
import com.caturindo.preference.Prefuser
import com.caturindo.utils.ApiInterface
import com.caturindo.utils.ServiceGenerator
import com.google.gson.Gson
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_team.*
import kotlinx.android.synthetic.main.fragment_search_bar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamActivity : BaseActivity(), TeamContract.View, AdapterTeam.OnListener, AdapterGroup.OnListener {
    private var menuMore: ImageView? = null
    private var menuList: ImageView? = null
    private var userAvatar: ImageView? = null
    private var rvTeams: RecyclerView? = null
    private var adapter: TeamItemAdapter? = null
    private var adapterTeam: AdapterTeam? = null
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
        //presenter.getTeamMember(Prefuser().getUser()?.id.toString())
        presenter.getUser()
        getGrup()
    }

    private fun bindView() {

        menuList = findViewById(R.id.img_menu)
        menuMore = findViewById(R.id.img_more)
    }

    private fun setupButtonAction() {
        menuList?.setOnClickListener { finish() }
        menuMore?.setOnClickListener {
            if (Prefuser().getUser()?.role.equals("3")) {
                showInfoMessage("Anda tidak mempunyai akses")
            } else {
                startActivity(Intent(this@TeamActivity, AddGrupActivity::class.java))
            }
        }
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
        showLongSuccessMessage(msg)
        adapterTeam?.notifyDataSetChanged()
        presenter.getTeamMember(Prefuser().getUser()?.id.toString())
    }

    override fun dataEmpty() {
        paren_data_empty.visibility = View.VISIBLE
    }

    override fun onSuccessGetTeam(data: List<MemberItem>) {

        adapterTeam = AdapterTeam(this, data as MutableList<MemberItem>, this)
        rv_team.layoutManager = LinearLayoutManager(this)
        rv_team.adapter = adapterTeam
        adapterTeam?.notifyDataSetChanged()
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


    fun getGrup() {
        val api = ServiceGenerator.createService(
                ApiInterface::class.java,
                Constant.USERNAME,
                Constant.PASS
        )
        progress_circular.visibility = View.VISIBLE
        api.getGroup(Prefuser().getUser()?.id).enqueue(object : Callback<BaseResponse<List<ResponseGroupDto>>> {
            override fun onFailure(call: Call<BaseResponse<List<ResponseGroupDto>>>, t: Throwable) {
                progress_circular.visibility = View.GONE
                showLongErrorMessage("Gagal dapatkan data grup")
            }

            override fun onResponse(call: Call<BaseResponse<List<ResponseGroupDto>>>, response: Response<BaseResponse<List<ResponseGroupDto>>>) {
                progress_circular.visibility = View.GONE
                if (response.isSuccessful) {
                    if (response.body()?.status == true) {
                        setDataGrup(response.body()?.data as MutableList<ResponseGroupDto>)
                    }else{
                        setDataGrup(response.body()?.data as MutableList<ResponseGroupDto>)
                    }
                }
            }
        })
    }

    fun deleteGrup(id : String){
        val api = ServiceGenerator.createService(
                ApiInterface::class.java,
                Constant.USERNAME,
                Constant.PASS
        )
        progress_circular.visibility = View.VISIBLE
        api.deleteGroup(id).enqueue(object : Callback<BaseResponseOther>{
            override fun onFailure(call: Call<BaseResponseOther>, t: Throwable) {
                progress_circular.visibility = View.GONE
                showLongErrorMessage("Gagal hapus grup")
            }

            override fun onResponse(call: Call<BaseResponseOther>, response: Response<BaseResponseOther>) {
                progress_circular.visibility = View.GONE
                if (response.isSuccessful){

                    if (response.body()?.status == true){
                        showLongSuccessMessage("Berhasil hapus grup")
                        getGrup()
                    }else{
                        showLongErrorMessage("Gagal hapus grup")
                    }
                }
            }
        })
    }

    private fun setDataGrup(mutableList: MutableList<ResponseGroupDto>) {
        val adapter = AdapterGroup(this@TeamActivity, mutableList, this)
        rv_team.layoutManager = LinearLayoutManager(this)
        rv_team.adapter = adapter
        adapter?.notifyDataSetChanged()
        rv_team.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))


        if (mutableList.isNullOrEmpty()) {
            paren_data_empty.visibility = View.VISIBLE
        } else {

            paren_data_empty.visibility = View.GONE
        }


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
                presenter.deleteTeam(AddTeamRequest(data.idMember, Prefuser().getUser()?.id))
            }
            builder.setNegativeButton("tidak", null)
            builder.show()
        }
    }


    override fun onClickGrup(data: ResponseGroupDto) {
        startActivity(Intent(this@TeamActivity, GrupTeamActivity::class.java)
                .putExtra("ID",data.id)
                .putExtra("NAMA",data.namaTeam)

        )
    }

    override fun onDeleteGrup(data: ResponseGroupDto) {
        deleteGrup(data.id.toString())
    }
}