package com.caturindo.activities.login

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

class LoginPresenter(private val view: LoginContract.View) : LoginContract.Presenter {

    private var data: RegisterDto = RegisterDto()
    var context: Context? = null
    override fun getLogin(email: String, pass: String) {
        view.showProgress()
        val api = ServiceGenerator.createService(
                ApiInterface::class.java,
                Constant.USERNAME,
                Constant.PASS
        )

        api.postLogin(email, pass).enqueue(object : Callback<BaseResponse<RegisterDto>> {
            override fun onFailure(call: Call<BaseResponse<RegisterDto>>, t: Throwable) {
                view.hideProgress()
                view.onErrorGetData("Gagal request")
                Log.e("TAG","gagal login ${t.message}")
            }

            override fun onResponse(call: Call<BaseResponse<RegisterDto>>, response: Response<BaseResponse<RegisterDto>>) {
                view.hideProgress()
                if (response.isSuccessful) {

                    if (response.body()?.status?.equals(true)!!) {

                        response.body()?.data.let {
                            if (it != null) {
                                data = it
                                view.onSuccessGet(data)
                            }else{
                                view.onErrorGetData("Gagal Login, ${response.body()?.message}")
                            }
                        }


                    } else {
                        view.onErrorGetData("Gagal Login, ${response.body()?.message}")
                    }
                } else {
                    view.onErrorGetData("Gagal Login, ${response.body()?.message}")
                }
            }

        })
    }

}