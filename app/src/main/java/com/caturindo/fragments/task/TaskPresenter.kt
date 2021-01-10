package com.caturindo.fragments.task

import android.content.Context
import android.util.Log
import com.caturindo.constant.Constant
import com.caturindo.models.BaseResponse
import com.caturindo.models.RegisterDto
import com.caturindo.models.TaskDto
import com.caturindo.utils.ApiInterface
import com.caturindo.utils.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskPresenter(private val view: TaskContract.View) : TaskContract.Presenter {

   
    var context: Context? = null
    override fun getTask() {
        view.showProgress()
        val api = ServiceGenerator.createService(
                ApiInterface::class.java,
                Constant.USERNAME,
                Constant.PASS
        )
        api.task.enqueue(object : Callback<BaseResponse<List<TaskDto>>>{
            override fun onFailure(call: Call<BaseResponse<List<TaskDto>>>, t: Throwable) {
                view.onErrorGetData("Gagal request data")
            }

            override fun onResponse(call: Call<BaseResponse<List<TaskDto>>>, response: Response<BaseResponse<List<TaskDto>>>) {
                view.hideProgress()
                if (response.isSuccessful) {
                    if (response.body()?.data != null) {
                        var data = response.body()!!.data
                        view.onSuccessGet(data as MutableList<TaskDto>)
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