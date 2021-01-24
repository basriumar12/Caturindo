package com.caturindo.fragments.room

import com.caturindo.models.RoomDto


interface RoomContract {
    interface View {
        fun showProgress()
        fun hideProgress()
        fun onSuccessGet(data : MutableList<RoomDto>)
        fun onErrorGetData(msg: String?)
    }

    interface Presenter {
        fun getRoom(starTime : String, endTime : String, date : String)
        fun getAllRoom()

    }
}