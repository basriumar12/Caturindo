package com.caturindo.activities.meeting.add_team

import android.content.Context
import android.util.Log
import com.caturindo.activities.meeting.model.AddMemberMeetingDto
import com.caturindo.activities.task.add_team.model.AddMemberTaskDto
import com.caturindo.constant.Constant
import com.caturindo.models.BaseResponse
import com.caturindo.models.RegisterDto
import com.caturindo.models.UserDto
import com.caturindo.utils.ApiInterface
import com.caturindo.utils.ServiceGenerator
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddTeamMeetingPresenter(private val view: AddTeamMeetingContract.View) : AddTeamMeetingContract.Presenter {
    val api = ServiceGenerator.createService(
            ApiInterface::class.java,
            Constant.USERNAME,
            Constant.PASS
    )

    var context: Context? = null
    override fun getUser() {

        view.showProgress()


        api.allDataUser.enqueue(object : Callback<BaseResponse<List<UserDto>>> {
            override fun onFailure(call: Call<BaseResponse<List<UserDto>>>, t: Throwable) {
                view.hideProgress()
                view.onErrorGetData("Gagal dapatkan data user")
            }

            override fun onResponse(call: Call<BaseResponse<List<UserDto>>>, response: Response<BaseResponse<List<UserDto>>>) {
                view.hideProgress()
                Log.e("TAG","data ${Gson().toJson(response?.body()?.data)}")
                if (response.isSuccessful) {
                    if (response.body()?.status == true) {

                        response.body()?.data?.let { view.onSuccessGet(it) }
                    } else {
                        view.onErrorGetData(response.body()?.message.toString())
                    }
                }
            }
        })

    }

    override fun addTeam(username: String, idMeeting: String) {

        view.showProgress()
        api.postAddMemberMeeting(username,idMeeting).enqueue(object :Callback<BaseResponse<AddMemberMeetingDto>>{
            override fun onFailure(call: Call<BaseResponse<AddMemberMeetingDto>>, t: Throwable) {
                view.hideProgress()
                view.faildAddMember("Gagal request data")
            }

            override fun onResponse(call: Call<BaseResponse<AddMemberMeetingDto>>, response: Response<BaseResponse<AddMemberMeetingDto>>) {
               view.hideProgress()
                if (response.isSuccessful) {
                    if (response.body()?.status == true) {

                        response.body()?.data?.let { view.suksesAddMember("Berhasil menambahkan", it) }
                    } else {
                        view.faildAddMember("Gagal, "+response.body()?.message)

                    }
                }else{
                    view.faildAddMember("Gagal, "+response.body()?.message)
                }
            }
        })
    }

}