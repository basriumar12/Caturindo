package com.caturindo.activities.team.add

import com.caturindo.models.RegisterDto
import com.caturindo.models.UserDto


interface AddTeamContract {
    interface View {
        fun showProgress()
        fun hideProgress()
        fun onSuccessGet(data : List<UserDto>)
        fun onErrorGetData(msg: String?)
    }

    interface Presenter {
        fun getUser()

    }
}