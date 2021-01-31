package com.caturindo.activities.task

import android.content.Context
import android.util.Log
import android.view.View
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.androidnetworking.interfaces.UploadProgressListener
import com.caturindo.activities.meeting.create.model.UploadDto
import com.caturindo.constant.Constant
import com.caturindo.models.*
import com.caturindo.preference.Prefuser
import com.caturindo.utils.ApiInterface
import com.caturindo.utils.ServiceGenerator
import kotlinx.android.synthetic.main.activity_create_meeting.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.io.File


class CreateTaskPresenter(private val view: CreatingTaskContract.View) : CreatingTaskContract.Presenter {


    var context: Context? = null
    val api = ServiceGenerator.createService(
            ApiInterface::class.java,
            Constant.USERNAME,
            Constant.PASS
    )
    override fun uploadFile(file: MultipartBody.Part) {

        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                val api = ServiceGenerator.createService(
                        ApiInterface::class.java,
                        Constant.USERNAME,
                        Constant.PASS
                )

                view.showProgress()
                api.uploadFIle(file).enqueue(object : retrofit2.Callback<BaseResponse<UploadDto>> {
                    override fun onFailure(call: Call<BaseResponse<UploadDto>>, t: Throwable) {
                        view.hideProgress()
                        view.failUpload("Gagal upload file")

                    }

                    override fun onResponse(call: Call<BaseResponse<UploadDto>>, response: Response<BaseResponse<UploadDto>>) {
                        view.hideProgress()
                        if (response.isSuccessful) {
                            if (response.body()?.status == true) {
                                val data = response.body()?.data
                                data?.let { view.successUpload("Berhasil Upload", it) }
                            }
                        } else {
                            view.failUpload("Gagal, ${response.body()?.message.toString()}")
                        }
                    }
                })
            }
        }






    }


    override fun postCreate(taskRequest: TaskRequest) {
        view.showProgress()

        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                
                api.postTask(taskRequest).enqueue(object : retrofit2.Callback<BaseResponse<TaskDto>> {
                    override fun onFailure(call: Call<BaseResponse<TaskDto>>, t: Throwable) {
                     Log.e("TAG","gagal create booking ${t.message}")
                        view.hideProgress()
                        view.failCreate("Gagal create data")

                    }

                    override fun onResponse(call: Call<BaseResponse<TaskDto>>, response: Response<BaseResponse<TaskDto>>) {


                        if (response.isSuccessful){
                            if (response.body()?.status == true){
                                   view.succesCreate(response.body()?.message.toString())
                            }else{
                                view.failCreate(response.body()?.message.toString())
                            }
                        }else{

                            view.failCreate(response.body()?.message.toString())
                        }
                    }
                })

            }
            }
    }

  
}