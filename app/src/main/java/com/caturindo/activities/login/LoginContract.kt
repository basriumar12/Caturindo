package com.caturindo.activities.login

import com.caturindo.models.RegisterDto


interface LoginContract {
    interface View {
        fun showProgress()
        fun hideProgress()
        fun onSuccessGet(data : RegisterDto?)
        fun onErrorGetData(msg: String?)
    }

    interface Presenter {
        fun getLogin(email : String, pass : String, fcm : String)

    }
}