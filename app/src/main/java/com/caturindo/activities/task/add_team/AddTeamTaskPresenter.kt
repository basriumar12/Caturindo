package com.caturindo.activities.task.add_team

import android.content.Context
import android.util.Log
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

class AddTeamTaskPresenter(private val view: AddTeamTaskContract.View) : AddTeamTaskContract.Presenter {
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
                view.onErrorGetData("Gagal dapatkan data user, ada kesalahan jaringan atau akses ke server")
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

    override fun addTeam(idUser: String, idTask: String) {

        view.showProgress()
        api.postAddMemberTask(idUser,idTask).enqueue(object :Callback<BaseResponse<AddMemberTaskDto>>{
            override fun onFailure(call: Call<BaseResponse<AddMemberTaskDto>>, t: Throwable) {
                view.hideProgress()
                view.faildAddMember("Gagal request data, ada kesalahan jaringan atau akses ke server")
            }

            override fun onResponse(call: Call<BaseResponse<AddMemberTaskDto>>, response: Response<BaseResponse<AddMemberTaskDto>>) {
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