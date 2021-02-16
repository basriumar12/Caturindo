package com.caturindo.activities.notif

import android.content.Context
import android.util.Log
import com.caturindo.activities.notif.model.NotifDto
import com.caturindo.activities.team.model.AddTeamRequest
import com.caturindo.activities.team.model.MemberItem
import com.caturindo.activities.team.model.TeamMemberDto
import com.caturindo.constant.Constant
import com.caturindo.models.BaseResponse
import com.caturindo.models.BaseResponseOther
import com.caturindo.models.UserDto
import com.caturindo.models.UserDtoNew
import com.caturindo.utils.ApiInterface
import com.caturindo.utils.ServiceGenerator
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotifPresenter(private val view: NotifContract.View) : NotifContract.Presenter {


    var context: Context? = null
    val api = ServiceGenerator.createService(
            ApiInterface::class.java,
            Constant.USERNAME,
            Constant.PASS
    )

    override fun getNotif(idUser : String) {

        view.showProgress()
    

        api.getNotif(idUser).enqueue(object : Callback<BaseResponse<List<NotifDto>>> {
            override fun onFailure(call: Call<BaseResponse<List<NotifDto>>>, t: Throwable) {
                view.hideProgress()
                view.failt("Gagal dapatkan data notif, ada kesalahan jaringan atau akses ke server")
                Log.e("TAG","get notif ${t.localizedMessage}")
            }

            override fun onResponse(call: Call<BaseResponse<List<NotifDto>>>, response: Response<BaseResponse<List<NotifDto>>>) {
                view.hideProgress()
                if (response.isSuccessful) {
                    if (response.body()?.status == true) {
                        response.body()?.data?.let {
                            view.successData(response.body()?.data as ArrayList<NotifDto>) }
                    } else {
                        view.failt(response.body()?.message.toString())
                    }

                    if (response.body()?.data.isNullOrEmpty()){
                        view.dataEmpty()
                    }
                }else{
                    view.failt(response.body()?.message.toString())
                }
            }
        })

    }

}