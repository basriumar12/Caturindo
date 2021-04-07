package com.caturindo.activities.grup

import android.content.Context
import android.util.Log
import android.view.View
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.androidnetworking.interfaces.UploadProgressListener
import com.caturindo.activities.grup.model.ResponseGroupDto
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


class CreateGrupPresenter(private val view: CreatingGrupContract.View) : CreatingGrupContract.Presenter {


    var context: Context? = null
    val api = ServiceGenerator.createService(
            ApiInterface::class.java,
            Constant.USERNAME,
            Constant.PASS
    )



    override fun postCreate(namaGrup: String) {

        val idUser = Prefuser().getUser()?.id
        GlobalScope.launch {
            withContext(Dispatchers.Main) {

                api.postAddGroup(idUser, namaGrup).enqueue(object : retrofit2.Callback<BaseResponse<ResponseGroupDto>> {
                    override fun onFailure(call: Call<BaseResponse<ResponseGroupDto>>, t: Throwable) {
                        view.hideProgress()
                        view.failCreate("Gagal mengisi data, ada kesalahan jaringan atau akses ke server")

                    }

                    override fun onResponse(call: Call<BaseResponse<ResponseGroupDto>>, response: Response<BaseResponse<ResponseGroupDto>>) {
                        view.hideProgress()
                        if (response.isSuccessful) {
                            if (response.body()?.status == true) {
                                view.succesCreate("Sukses membuat grup")
                            }
                        } else {
                            view.failCreate("Gagal mengisi data, ada kesalahan jaringan atau akses ke server")

                        }
                    }
                })
            }

        }
    }

    override fun getGrup() {

    }

}