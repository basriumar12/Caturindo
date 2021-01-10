package com.caturindo.fragments.room

import android.content.Context
import android.util.Log
import com.caturindo.constant.Constant
import com.caturindo.models.BaseResponse
import com.caturindo.models.RegisterDto
import com.caturindo.models.RoomDto
import com.caturindo.models.TaskDto
import com.caturindo.utils.ApiInterface
import com.caturindo.utils.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoomPresenter(private val view: RoomContract.View) : RoomContract.Presenter {

   
    var context: Context? = null
    override fun getRoom(status : String) {
        view.showProgress()
        val api = ServiceGenerator.createService(
                ApiInterface::class.java,
                Constant.USERNAME,
                Constant.PASS
        )
        api.getRoom(status).enqueue(object : Callback<BaseResponse<List<RoomDto>>>{
            override fun onFailure(call: Call<BaseResponse<List<RoomDto>>>, t: Throwable) {
                view.onErrorGetData("Gagal request data")
            }

            override fun onResponse(call: Call<BaseResponse<List<RoomDto>>>, response: Response<BaseResponse<List<RoomDto>>>) {
                view.hideProgress()
                if (response.isSuccessful) {
                    if (response.body()?.data != null) {
                        var data = response.body()!!.data
                        view.onSuccessGet(data as MutableList<RoomDto>)
                    } else {
                        view.onErrorGetData("Gagal dapatkan data")
                    }
                } else {
                    view.onErrorGetData("Gagal dapatkan data, ${response.body()?.message}")
                }
            }
        })
    }


}