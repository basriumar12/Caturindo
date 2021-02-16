package com.caturindo.fragments.task

import android.content.Context
import android.util.Log
import com.caturindo.constant.Constant
import com.caturindo.models.BaseResponse
import com.caturindo.models.BaseResponseOther
import com.caturindo.models.TaskDto
import com.caturindo.models.UpdateTokenRequest
import com.caturindo.utils.ApiInterface
import com.caturindo.utils.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskPresenter(private val view: TaskContract.View) : TaskContract.Presenter {

    val api = ServiceGenerator.createService(
            ApiInterface::class.java,
            Constant.USERNAME,
            Constant.PASS
    )
    var context: Context? = null
    override fun getTask() {
        view.showProgress()

        api.task.enqueue(object : Callback<BaseResponse<List<TaskDto>>>{
            override fun onFailure(call: Call<BaseResponse<List<TaskDto>>>, t: Throwable) {
                view.onErrorGetData("Gagal request data")
                view.hideProgress()
            }

            override fun onResponse(call: Call<BaseResponse<List<TaskDto>>>, response: Response<BaseResponse<List<TaskDto>>>) {
                view.hideProgress()
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    if (response.body()?.status == true) {

                        view.onSuccessGet(data as MutableList<TaskDto>)
                    }

                    if (response.body()?.data.isNullOrEmpty() || response.body()?.status == false){
                        view.dataEmpty()
                    }
                } else {
                    view.onErrorGetData("Gagal dapatkan data, ${response.body()?.message}")
                }
            }
        })



    }

    override fun getUpdateToken(body: UpdateTokenRequest) {
        api.updateTokenUser(body).enqueue(object : Callback<BaseResponseOther>{
            override fun onFailure(call: Call<BaseResponseOther>, t: Throwable) {
            Log.e("TAG","token fail ${t.message}")
            }

            override fun onResponse(call: Call<BaseResponseOther>, response: Response<BaseResponseOther>) {
                Log.e("TAG","token succes ${response.body()?.message}")
            }
        })

    }


}