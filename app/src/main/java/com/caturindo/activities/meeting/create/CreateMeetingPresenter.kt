package com.caturindo.activities.meeting.create

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
import com.caturindo.models.BaseResponse
import com.caturindo.models.MeetingRequest
import com.caturindo.utils.ApiInterface
import com.caturindo.utils.ServiceGenerator
import kotlinx.android.synthetic.main.activity_create_meeting.*
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.io.File


class CreateMeetingPresenter(private val view: CreatingMeetingContract.View) : CreatingMeetingContract.Presenter {


    var context: Context? = null

    override fun uploadFile(file: MultipartBody.Part) {

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
                Log.e("TAG","gagal upload ${t.message}")

            }

            override fun onResponse(call: Call<BaseResponse<UploadDto>>, response: Response<BaseResponse<UploadDto>>) {
                view.hideProgress()
                if (response.isSuccessful) {
                    if (response.body()?.status?.equals(true)!!) {
                        val data = response.body()?.data
                        data?.let { view.successUpload("Berhasil Upload", it) }
                    }
                } else {
                        view.failUpload("Gagal, ${response.body()?.message.toString()}")
                }
            }
        })





    }


    override fun postCreate(meetingRequest: MeetingRequest) {

    }

}