package com.caturindo.activities.meeting.list

import android.content.Context
import android.util.Log
import com.caturindo.constant.Constant
import com.caturindo.models.BaseResponse
import com.caturindo.models.MeetingDtoNew
import com.caturindo.models.RoomDto
import com.caturindo.utils.ApiInterface
import com.caturindo.utils.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskMeetingPresenter(private val view: TaskMeetingContract.View) : TaskMeetingContract.Presenter {
    val api = ServiceGenerator.createService(
            ApiInterface::class.java,
            Constant.USERNAME,
            Constant.PASS
    )
   
    var context: Context? = null
    override fun getMeeting(status: String) {
        
        view.showProgress()
        api.getMeeting(status).enqueue(object : Callback<BaseResponse<List<MeetingDtoNew>>>{
            override fun onFailure(call: Call<BaseResponse<List<MeetingDtoNew>>>, t: Throwable) {
                Log.e("TAG","gagal meeting req ${t.message}")
                view.hideProgress()
                view.onErrorGetData("Gagal request data, ada kesalahan dari server")
            }

            override fun onResponse(call: Call<BaseResponse<List<MeetingDtoNew>>>, response: Response<BaseResponse<List<MeetingDtoNew>>>) {

                view.hideProgress()
                if (response.isSuccessful) {
                    if (response.body()?.status == true) {


                        if (!response.body()?.data.isNullOrEmpty()) {
                            var data = response.body()?.data
                            view.onSuccessGet(data as MutableList<MeetingDtoNew>)
                        }

                        if (response.body()?.data.isNullOrEmpty()) {
                           view.dataEmpty()
                        }


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