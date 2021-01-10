package com.caturindo.fragments.room

import com.caturindo.models.RegisterDto
import com.caturindo.models.RoomDto
import com.caturindo.models.TaskDto


interface RoomContract {
    interface View {
        fun showProgress()
        fun hideProgress()
        fun onSuccessGet(data : MutableList<RoomDto>)
        fun onErrorGetData(msg: String?)
    }

    interface Presenter {
        fun getRoom(status : String)

    }
}