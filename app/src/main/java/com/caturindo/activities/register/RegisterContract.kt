package com.caturindo.activities.register

import com.caturindo.models.RegisterDto


interface RegisterContract {
    interface View {
        fun showProgress()
        fun hideProgress()
        fun onSuccessGet(data : RegisterDto?)
        fun onErrorGetData(msg: String?)
    }

    interface Presenter {
        fun getRegister(email : String, pass : String, username : String, phone : String, role : String)

    }
}