package com.caturindo.activities.team.add

import android.content.Context
import android.util.Log
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

class AddTeamPresenter(private val view: AddTeamContract.View) : AddTeamContract.Presenter {


    var context: Context? = null
    override fun getUser() {

        view.showProgress()
        val api = ServiceGenerator.createService(
                ApiInterface::class.java,
                Constant.USERNAME,
                Constant.PASS
        )

        api.allDataUser.enqueue(object : Callback<BaseResponse<List<UserDto>>> {
            override fun onFailure(call: Call<BaseResponse<List<UserDto>>>, t: Throwable) {
                view.hideProgress()
                view.onErrorGetData("Gagal dapatkan data user")
            }

            override fun onResponse(call: Call<BaseResponse<List<UserDto>>>, response: Response<BaseResponse<List<UserDto>>>) {
                view.hideProgress()
                Log.e("TAG","data ${Gson().toJson(response?.body()?.data)}")
                if (response.isSuccessful) {
                    if (response.body()?.status?.equals(true)!!) {

                        response.body()?.data?.let { view.onSuccessGet(it) }
                    } else {
                        view.onErrorGetData(response.body()?.message.toString())
                    }
                }
            }
        })

    }

}