package com.caturindo.activities.team.edit

import android.content.Context
import android.util.Log
import android.view.View
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.androidnetworking.interfaces.UploadProgressListener
import com.caturindo.activities.meeting.create.model.UploadDto
import com.caturindo.activities.team.model.EditUserRequest
import com.caturindo.activities.team.model.UpdateUploadUserDto
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


class EditUserPresenter(private val view: EditUserContract.View) : EditUserContract.Presenter {


    var context: Context? = null
    val api = ServiceGenerator.createService(
            ApiInterface::class.java,
            Constant.USERNAME,
            Constant.PASS
    )

    override fun getUser(idUser: String) {
        view.showProgress()


        api.getUserDetail(idUser).enqueue(object : retrofit2.Callback<BaseResponse<UserDtoNew>> {
            override fun onFailure(call: Call<BaseResponse<UserDtoNew>>, t: Throwable) {
                view.hideProgress()
                view.onErrorGetData("Gagal dapatkan data user, ada kesalahan jaringan atau akses ke server")
            }

            override fun onResponse(call: Call<BaseResponse<UserDtoNew>>, response: Response<BaseResponse<UserDtoNew>>) {
                view.hideProgress()
                if (response.isSuccessful) {
                    if (response.body()?.status == true) {
                        response.body()?.data?.let { view.onSuccessGetDetail(it) }
                    } else {
                        view.onErrorGetData(response.body()?.message.toString())
                    }
                }
            }
        })
    }


    override fun editUser(data: EditUserRequest) {
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                GlobalScope.launch {
                    withContext(Dispatchers.Main) {

                        view.showProgress()

                        api.editUser(data).enqueue(object : retrofit2.Callback<BaseResponseOther>{
                            override fun onFailure(call: Call<BaseResponseOther>, t: Throwable) {
                                view.hideProgress()
                                view.onErrorGetData("gagal edit user, ada kesalahan jaringan atau akses ke server")
                            }

                            override fun onResponse(call: Call<BaseResponseOther>, response: Response<BaseResponseOther>) {


                                if (response.isSuccessful){
                                    if (response.body()?.status == true){

                                        view.onSuccessEditUser(response.body()?.message)
                                    }else{

                                        view.onErrorGetData("gagal ${response.body()?.message}")
                                    }
                                }else{
                                    view.onErrorGetData("gagal ${response.body()?.message}")
                                }
                            }

                        })

                    }
                }
            }
        }

    }

    override fun uploadImage(idUser: RequestBody, file: MultipartBody.Part) {
        view.showProgress()

        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                GlobalScope.launch {
                    withContext(Dispatchers.Main) {

                        view.showProgress()
                        api.uploadImageProfile(idUser, file).enqueue(object : retrofit2.Callback<BaseResponse<UpdateUploadUserDto>> {
                            override fun onFailure(call: Call<BaseResponse<UpdateUploadUserDto>>, t: Throwable) {
                                view.hideProgress()
                                view.onErrorGetData("gagal upload")

                            }

                            override fun onResponse(call: Call<BaseResponse<UpdateUploadUserDto>>, response: Response<BaseResponse<UpdateUploadUserDto>>) {
                                view.hideProgress()
                                if (response.isSuccessful) {
                                    if (response.body()?.status == true) {
                                        response.body()?.data?.let { view.onSuccessGet(it) }
                                    } else {
                                        view.onErrorGetData("Gagal, ${response.body()?.message.toString()}")
                                    }
                                } else {
                                    view.onErrorGetData("Gagal, ${response.body()?.message.toString()}")
                                }
                            }
                        })
                    }
                }


            }
        }
    }

    override fun uploadBackgroundImage(idUser: RequestBody, file: MultipartBody.Part) {
        view.showProgress()

        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                GlobalScope.launch {
                    withContext(Dispatchers.Main) {
                        val api = ServiceGenerator.createService(
                                ApiInterface::class.java,
                                Constant.USERNAME,
                                Constant.PASS
                        )

                        view.showProgress()
                        api.uploadBackgroundProfile(idUser, file).enqueue(object : retrofit2.Callback<BaseResponse<UpdateUploadUserDto>> {
                            override fun onFailure(call: Call<BaseResponse<UpdateUploadUserDto>>, t: Throwable) {
                                view.hideProgress()
                                view.onErrorGetData("gagal upload")

                            }

                            override fun onResponse(call: Call<BaseResponse<UpdateUploadUserDto>>, response: Response<BaseResponse<UpdateUploadUserDto>>) {
                                view.hideProgress()
                                if (response.isSuccessful) {
                                    if (response.body()?.status == true) {
                                        response.body()?.data?.let { view.onSuccessGetBackground(it) }
                                    } else {
                                        view.onErrorGetData("Gagal, ${response.body()?.message.toString()}")
                                    }
                                } else {
                                    view.onErrorGetData("Gagal, ${response.body()?.message.toString()}")
                                }
                            }
                        })
                    }
                }


            }
        }

    }


}