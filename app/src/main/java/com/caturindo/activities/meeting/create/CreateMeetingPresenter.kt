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
import com.caturindo.models.*
import com.caturindo.preference.Prefuser
import com.caturindo.utils.ApiInterface
import com.caturindo.utils.ServiceGenerator
import com.google.gson.Gson
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


class CreateMeetingPresenter(private val view: CreatingMeetingContract.View) : CreatingMeetingContract.Presenter {


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
                                data?.let { view.successUpload("${response.body()?.message.toString()}", it) }
                            }
                        } else {
                            view.failUpload("Gagal, ${response.body()?.message.toString()}")
                        }
                    }
                })
            }
        }


    }


    override fun postCreate(meetingRequest: MeetingRequest, idParentMeeting: String) {
        view.showProgress()
        Log.e("TAG","meeting req ${Gson().toJson(meetingRequest)}")
        GlobalScope.launch {
            withContext(Dispatchers.Main) {

                api.postBooking(Prefuser().getBooking()).enqueue(object : retrofit2.Callback<BaseResponse<ArrayList<BookingDto>>> {
                    override fun onFailure(call: Call<BaseResponse<ArrayList<BookingDto>>>, t: Throwable) {
                        Log.e("TAG", "gagal create booking ${t.message}")
                        view.hideProgress()
                        view.failCreate("Gagal booking, ada kesalahan jaringan atau akses ke server")

                    }

                    override fun onResponse(call: Call<BaseResponse<ArrayList<BookingDto>>>, response: Response<BaseResponse<ArrayList<BookingDto>>>) {


                        if (response.isSuccessful) {
                            if (response.body()?.status == true) {

                                //validasi create meeting (1) and submeeting(0)
                                if (Prefuser().getvalidateMeeting().equals("1")) {
                                    createMeeting(meetingRequest, response.body()?.data?.get(0)?.id.toString())
                                } else {
                                    createSubMeeting(meetingRequest, response.body()?.data?.get(0)?.id.toString(), idParentMeeting, meetingRequest.id_group.toString())
                                }
                            } else {
                                view.failCreate(response.body()?.message.toString())
                            }
                        } else {

                            view.failCreate(response.body()?.message.toString())
                        }
                    }
                })

            }
        }
    }

    private fun createSubMeeting(meetingRequest: MeetingRequest, idBooking: String, idParentMeeting: String, idGrup : String) {
        view.showProgress()
        val idBookingData = idBooking
        val meetingSubRequest = MeetingSubRequest(
                idBookingData,
                meetingRequest.date,
                idParentMeeting,
                meetingRequest.description,
                meetingRequest.location,
                meetingRequest.idUser.toString(),
                meetingRequest.time,
                meetingRequest.tag,
                meetingRequest.title,
                meetingRequest.idFile.toString(),
                idGrup
        )
        Log.e("TAG","submeeting req ${Gson().toJson(meetingSubRequest)}")
        GlobalScope.launch {
            withContext(Dispatchers.Main) {

                api.postMeetingSub(meetingSubRequest).enqueue(object : retrofit2.Callback<BaseResponse<MeetingSubDtoNew>>{
                    override fun onFailure(call: Call<BaseResponse<MeetingSubDtoNew>>, t: Throwable) {
                        Log.e("TAG", "gagal create booking ${t.message}")
                        view.hideProgress()
                        view.failCreate("Gagal mengisi data, ada kesalahan jaringan atau akses ke server")
                    }

                    override fun onResponse(call: Call<BaseResponse<MeetingSubDtoNew>>, response: Response<BaseResponse<MeetingSubDtoNew>>) {
                        view.hideProgress()
                        if (response.isSuccessful) {
                            if (response.body()?.status == true) {
                                view.succesCreate("Berhasil membuat meeting")
                            } else {
                                view.failCreate("Gagal, " + response.body()?.message.toString())
                            }
                        } else {

                            view.failCreate("Gagal, " + response.body()?.message.toString())
                        }
                    }
                })

            }
        }
    }

    private fun createMeeting(meetingRequest: MeetingRequest, idBooking: String) {

        meetingRequest.idBooking = idBooking
        GlobalScope.launch {
            withContext(Dispatchers.Main) {

                api.postMeeting(meetingRequest).enqueue(object : retrofit2.Callback<BaseResponse<MeetingDto>> {
                    override fun onFailure(call: Call<BaseResponse<MeetingDto>>, t: Throwable) {
                        Log.e("TAG", "gagal create booking ${t.message}")
                        view.hideProgress()
                        view.failCreate("Gagal mengisi data, ada kesalahan jaringan atau akses ke server")

                    }

                    override fun onResponse(call: Call<BaseResponse<MeetingDto>>, response: Response<BaseResponse<MeetingDto>>) {

                        view.hideProgress()
                        if (response.isSuccessful) {
                            if (response.body()?.status == true) {
                                view?.succesCreate("Berhasil membuat meeting")
                            } else {
                                view.failCreate("Gagal, " + response.body()?.message.toString())
                            }
                        } else {

                            view.failCreate("Gagal, " + response.body()?.message.toString())
                        }
                    }
                })

            }

        }
    }

}