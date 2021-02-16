package com.caturindo.activities.register

import android.content.Context
import android.util.Log
import com.caturindo.constant.Constant
import com.caturindo.models.BaseResponse
import com.caturindo.models.RegisterDto
import com.caturindo.utils.ApiInterface
import com.caturindo.utils.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterPresenter(private val view: RegisterContract.View) : RegisterContract.Presenter {

    private var data: RegisterDto = RegisterDto()
    var context: Context? = null
    override fun getRegister(email : String, pass : String, username : String, phone : String, role : String) {
        view.showProgress()
        val api = ServiceGenerator.createService(
                ApiInterface::class.java,
                Constant.USERNAME,
                Constant.PASS
        )


        api.postRegister(email,pass,username,phone,role.toInt()).enqueue(object : Callback<BaseResponse<RegisterDto>>{
            override fun onFailure(call: Call<BaseResponse<RegisterDto>>, t: Throwable) {
                view.hideProgress()
                view.onErrorGetData("Gagal Request, ada kesalahan jaringan atau ke server")
            }

            override fun onResponse(call: Call<BaseResponse<RegisterDto>>, response: Response<BaseResponse<RegisterDto>>) {
                view.hideProgress()
                if (response.isSuccessful) {
                    if (response.body()?.status == true) {
                        response.body()?.data.let {
                            if (it != null) {
                                data = it
                            }
                        }
                        view.onSuccessGet(data)
                    } else {
                        view.onErrorGetData("Gagal, , ${response.body()?.message}")
                    }
                } else {
                    view.onErrorGetData("Gagal Register, ${response.body()?.message}")
                }
            }
        })
    }

}