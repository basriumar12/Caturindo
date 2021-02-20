package com.caturindo.activities.team

import android.content.Context
import android.util.Log
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

class TeamPresenter(private val view: TeamContract.View) : TeamContract.Presenter {


    var context: Context? = null
    val api = ServiceGenerator.createService(
            ApiInterface::class.java,
            Constant.USERNAME,
            Constant.PASS
    )

    override fun getUser(idUser : String) {

        view.showProgress()
    

        api.getUserDetail(idUser).enqueue(object : Callback<BaseResponse<UserDtoNew>> {
            override fun onFailure(call: Call<BaseResponse<UserDtoNew>>, t: Throwable) {
                view.hideProgress()
                view.onErrorGetData("Gagal Request, ada kesalahan jaringan atau ke server")
            }

            override fun onResponse(call: Call<BaseResponse<UserDtoNew>>, response: Response<BaseResponse<UserDtoNew>>) {
                view.hideProgress()
                if (response.isSuccessful) {
                    if (response.body()?.status == true) {
                        response.body()?.data?.let { view.onSuccessGetDetail(it) }
                    } else {
                        view.onErrorGetDataDetail(response.body()?.message.toString())
                    }
                }
            }
        })

    }

    override fun getUser() {

        view.showProgress()
  

        api.allDataUsers.enqueue(object : Callback<BaseResponse<List<UserDto>>> {
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

    override fun postTeam(data: AddTeamRequest) {
        view.showProgress()
      
        api.postAddTeam(data).enqueue(object  : Callback<BaseResponseOther>{
            override fun onFailure(call: Call<BaseResponseOther>, t: Throwable) {
                view.hideProgress()
                view.onErrorGetData("Gagal tambahkan data member, ada kesalahan jaringan atau akses ke server")
            }

            override fun onResponse(call: Call<BaseResponseOther>, response: Response<BaseResponseOther>) {
                view.hideProgress()
                Log.e("TAG","data ${Gson().toJson(response?.body()?.status)}")
                if (response.isSuccessful) {
                    if (response.body()?.status == true) {
                            view.onSuccessAddTeam(response.body()?.message.toString())
                    } else {
                        view.onErrorGetData(response.body()?.message.toString())
                    }
                }
            }
        })

    }

    override fun deleteTeam(data: AddTeamRequest) {
        view.showProgress()

        api.postDeleteTeam(data).enqueue(object  : Callback<BaseResponseOther>{
            override fun onFailure(call: Call<BaseResponseOther>, t: Throwable) {
                view.hideProgress()
                view.onErrorGetData("Gagal hapus data member, ada kesalahan jaringan atau akses ke server")
            }

            override fun onResponse(call: Call<BaseResponseOther>, response: Response<BaseResponseOther>) {
                view.hideProgress()
                Log.e("TAG","data ${Gson().toJson(response?.body()?.status)}")
                if (response.isSuccessful) {
                    if (response.body()?.status == true) {
                            view.onSuccessAddTeam(response.body()?.message.toString())
                    } else {
                        view.onErrorGetData(response.body()?.message.toString())
                    }
                }
            }
        })

    }

    override fun getTeamMember(idUser : String) {
        
        view.showProgress()
        api.getTeamMember(idUser).enqueue(object : Callback<BaseResponse<TeamMemberDto>>{
            override fun onFailure(call: Call<BaseResponse<TeamMemberDto>>, t: Throwable) {
                view.hideProgress()
                view.onErrorGetData("Gagal dapatkan data member, ada kesalahan akses ke server")
            }

            override fun onResponse(call: Call<BaseResponse<TeamMemberDto>>, response: Response<BaseResponse<TeamMemberDto>>) {
                view.hideProgress()
                if (response.isSuccessful) {
                    if (response.body()?.status == true) {
                        view.onSuccessGetTeam(response.body()?.data?.member as List<MemberItem>)
                    } else {

                        view.dataEmpty()
                        //view.onErrorGetData(response.body()?.message.toString())
                    }

                    if (response.body()?.data?.member.isNullOrEmpty()){
                        view.dataEmpty()
                    }
                }else{
                    view.onErrorGetData(response.body()?.message.toString())
                }
            }
        })
    }

}