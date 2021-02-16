package com.caturindo.activities.meeting.detail

import android.content.Context
import com.caturindo.activities.meeting.model.AddMeetingCommentRequest
import com.caturindo.activities.meeting.model.MeetingCommentDto
import com.caturindo.activities.task.detail.model.AddCommentDto
import com.caturindo.activities.task.detail.model.AddCommentRequest
import com.caturindo.activities.task.detail.model.CommentDto
import com.caturindo.constant.Constant
import com.caturindo.models.*
import com.caturindo.utils.ApiInterface
import com.caturindo.utils.ServiceGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CommentMeetingPresenter(private val view: CommentMeetingContract.View) : CommentMeetingContract.Presenter {


    var context: Context? = null
    val api = ServiceGenerator.createService(
            ApiInterface::class.java,
            Constant.USERNAME,
            Constant.PASS
    )

    override fun updateMeeting(idMeeting: String) {
        view.showProgress()
        GlobalScope.launch {
            withContext(Dispatchers.Main) {

                api.updateStatusMeeting(CancelMeetingRequest(idMeeting)).enqueue(object :Callback<BaseResponseOther>{
                    override fun onFailure(call: Call<BaseResponseOther>, t: Throwable) {
                        view.failGetComment("Gagal update meeting, ada kesalahan jaringan atau akses ke server")
                        view.hideProgress()
                    }

                    override fun onResponse(call: Call<BaseResponseOther>, response: Response<BaseResponseOther>) {
                        view.hideProgress()
                        if (response.isSuccessful) {
                            if (response.body()?.status == true) {
                                view.successPostComment("Berhasil update")
                            } else {
                                view.failGetComment("gagal, ${response.body()?.message}")
                            }
                        } else {

                            view.failGetComment("gagal, ${response.body()?.message}")
                        }

                    }
                })

            }
        }
    }

    override fun cancelMeeting(idMeeting: String) {
        view.showProgress()
        GlobalScope.launch {
            withContext(Dispatchers.Main) {

                api.cancelMeeting(CancelMeetingRequest(idMeeting)).enqueue(object :Callback<BaseResponseOther>{
                    override fun onFailure(call: Call<BaseResponseOther>, t: Throwable) {
                        view.failGetComment("Gagal cancel meeting, ada kesalahan jaringan atau akses ke server")
                        view.hideProgress()
                    }

                    override fun onResponse(call: Call<BaseResponseOther>, response: Response<BaseResponseOther>) {
                        view.hideProgress()
                        if (response.isSuccessful) {
                            if (response.body()?.status == true) {
                                view.succsesCancel("Berhasil cancel")
                            } else {
                                view.failGetComment("gagal, ${response.body()?.message}")
                            }
                        } else {

                            view.failGetComment("gagal, ${response.body()?.message}")
                        }

                    }
                })
            }
        }
    }


    override fun getComment(idMeeting: String) {

        view.showProgress()
        GlobalScope.launch {
            withContext(Dispatchers.Main) {

                api.getMeetingComment(idMeeting).enqueue(object : retrofit2.Callback<BaseResponse<List<CommentDto>>> {
                    override fun onFailure(call: Call<BaseResponse<List<CommentDto>>>, t: Throwable) {
                        view.hideProgress()
                        view.failGetComment("Gagal dapatkan komentar, ada kesalahan jaringan atau akses ke server")
                    }

                    override fun onResponse(call: Call<BaseResponse<List<CommentDto>>>, response: Response<BaseResponse<List<CommentDto>>>) {
                        view.hideProgress()
                        if (response.isSuccessful) {
                            if (response.body()?.status == true) {
                                view.successData(response.body()?.data as ArrayList<CommentDto>)
                            } else {
                                //view.failGetComment("gagal, ${response.body()?.message}")
                            }
                        } else {

                            //view.failGetComment("gagal, ${response.body()?.message}")
                        }
                    }
                })

            }
        }
    }

    override fun postComment(addCommentRequest: AddMeetingCommentRequest) {
        view.showProgress()
        GlobalScope.launch {
            withContext(Dispatchers.Main) {

                api.postMeetingComment(addCommentRequest).enqueue(object : Callback<BaseResponse<MeetingCommentDto>> {
                    override fun onFailure(call: Call<BaseResponse<MeetingCommentDto>>, t: Throwable) {
                        view.hideProgress()
                        view.failPostComment("gagal create data, ada kesalahan jaringan atau akses ke server")
                    }

                    override fun onResponse(call: Call<BaseResponse<MeetingCommentDto>>, response: Response<BaseResponse<MeetingCommentDto>>) {

                        view.hideProgress()
                        if (response.isSuccessful) {
                            if (response.body()?.status == true) {
                                view.successPostComment("Berhasil, ${response.body()?.message}")
                            } else {
                                view.failPostComment("gagal, ${response.body()?.message}")
                            }
                        } else {

                            view.failPostComment("gagal, ${response.body()?.message}")
                        }
                    }
                })
            }
        }
    }


}