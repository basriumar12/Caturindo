package com.caturindo.activities.meeting.list

import android.content.Context
import android.util.Log
import com.caturindo.constant.Constant
import com.caturindo.models.BaseResponse
import com.caturindo.models.MeetingSubDtoNew
import com.caturindo.models.RoomDto
import com.caturindo.utils.ApiInterface
import com.caturindo.utils.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskSubMeetingPresenter(private val view: TaskSubMeetingContract.View) : TaskSubMeetingContract.Presenter {
    val api = ServiceGenerator.createService(
            ApiInterface::class.java,
            Constant.USERNAME,
            Constant.PASS
    )
   
    var context: Context? = null
    override fun getMeeting(status: String) {
        
        view.showProgress()
        api.getSubMeeting(status).enqueue(object : Callback<BaseResponse<List<MeetingSubDtoNew>>>{
            override fun onFailure(call: Call<BaseResponse<List<MeetingSubDtoNew>>>, t: Throwable) {
                Log.e("TAG","gagal meeting req ${t.message}")
                view.hideProgress()
                view.onErrorGetData("Gagal request data")
            }

            override fun onResponse(call: Call<BaseResponse<List<MeetingSubDtoNew>>>, response: Response<BaseResponse<List<MeetingSubDtoNew>>>) {

                view.hideProgress()
                if (response.isSuccessful) {
                    if (response.body()?.data != null) {
                        var data = response.body()?.data
                        view.onSuccessGet(data as MutableList<MeetingSubDtoNew>)
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