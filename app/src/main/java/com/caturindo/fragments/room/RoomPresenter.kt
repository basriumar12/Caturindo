package com.caturindo.fragments.room

import android.content.Context
import com.caturindo.constant.Constant
import com.caturindo.models.BaseResponse
import com.caturindo.models.RoomDto
import com.caturindo.utils.ApiInterface
import com.caturindo.utils.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoomPresenter(private val view: RoomContract.View) : RoomContract.Presenter {
    val api = ServiceGenerator.createService(
            ApiInterface::class.java,
            Constant.USERNAME,
            Constant.PASS
    )
   
    var context: Context? = null
    override fun getRoom(starTime : String, endTime : String, date : String) {
        view.showProgress()

        api.getRoom(starTime,endTime,date).enqueue(object : Callback<BaseResponse<List<RoomDto>>>{
            override fun onFailure(call: Call<BaseResponse<List<RoomDto>>>, t: Throwable) {
                view.onErrorGetData("Gagal request data")
            }

            override fun onResponse(call: Call<BaseResponse<List<RoomDto>>>, response: Response<BaseResponse<List<RoomDto>>>) {
                view.hideProgress()
                if (response.isSuccessful) {
                    if (response.body()?.data != null) {
                        var data = response.body()?.data
                        view.onSuccessGet(data as MutableList<RoomDto>)
                    } else {
                        view.onErrorGetData("Gagal, ${response.message()}")
                    }
                } else {
                    view.onErrorGetData("Gagal, ${response.message()}")
                }
            }
        })
    }

    override fun getAllRoom() {
        view.showProgress()

        api.roomAll.enqueue(object : Callback<BaseResponse<List<RoomDto>>>{
            override fun onFailure(call: Call<BaseResponse<List<RoomDto>>>, t: Throwable) {
                view.onErrorGetData("Gagal request data")
            }

            override fun onResponse(call: Call<BaseResponse<List<RoomDto>>>, response: Response<BaseResponse<List<RoomDto>>>) {
                view.hideProgress()
                if (response.isSuccessful) {
                    if (response.body()?.data != null) {
                        var data = response.body()?.data
                        view.onSuccessGet(data as MutableList<RoomDto>)
                    } else {
                        view.onErrorGetData("Gagal, ${response.message()}")
                    }
                } else {
                    view.onErrorGetData("Gagal, ${response.message()}")
                }
            }
        })
    }


}