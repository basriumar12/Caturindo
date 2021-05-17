package com.caturindo.fragments.transport

import android.content.Context
import com.caturindo.constant.Constant
import com.caturindo.models.BaseResponse
import com.caturindo.models.RoomDto
import com.caturindo.models.TransportDto
import com.caturindo.utils.ApiInterface
import com.caturindo.utils.ServiceGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransportPresenter(private val view: TransportContract.View) : TransportContract.Presenter {
    val api = ServiceGenerator.createService(
            ApiInterface::class.java,
            Constant.USERNAME,
            Constant.PASS
    )


   
    var context: Context? = null
    override fun getTransport(starTime: String, endTime: String, date: String) {

        GlobalScope.launch {
            withContext(Dispatchers.Main) {

                view.showProgress()
                api.getTransport(starTime, endTime, date).enqueue(object : Callback<BaseResponse<List<TransportDto>>> {
                    override fun onFailure(call: Call<BaseResponse<List<TransportDto>>>, t: Throwable) {
                        view.hideProgress()
                        view.onErrorGetData("Gagal, ada kesalahan akses ke server")
                    }

                    override fun onResponse(call: Call<BaseResponse<List<TransportDto>>>, response: Response<BaseResponse<List<TransportDto>>>) {
                       view.hideProgress()
                        if (response.isSuccessful) {
                            if (response.body()?.status == true) {
                                var data = response.body()?.data
                                view.onSuccessGet(data as MutableList<TransportDto>)
                            } else {

                                view.onErrorGetData("${response?.body()?.message.toString()}")
                            }
                        } else {

                            view.onErrorGetData("${response?.body()?.message.toString()}")
                        }
                    }
                })

            }}
    }

    override fun getAllTransport() {
        GlobalScope.launch {
            withContext(Dispatchers.Main) {

                view.showProgress()
                api.allTransport.enqueue(object : Callback<BaseResponse<List<TransportDto>>> {
                    override fun onFailure(call: Call<BaseResponse<List<TransportDto>>>, t: Throwable) {
                        view.hideProgress()
                        view.onErrorGetData("Gagal, ada kesalahan akses ke server")
                    }

                    override fun onResponse(call: Call<BaseResponse<List<TransportDto>>>, response: Response<BaseResponse<List<TransportDto>>>) {
                        view.hideProgress()
                        if (response.isSuccessful) {
                            if (response.body()?.status == true) {
                                var data = response.body()?.data
                                view.onSuccessGet(data as MutableList<TransportDto>)
                            } else {

                                view.onErrorGetData("${response?.body()?.message.toString()}")
                            }
                        } else {

                            view.onErrorGetData("${response?.body()?.message.toString()}")
                        }
                    }
                })

            }}

    }


}