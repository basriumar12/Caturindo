package com.caturindo.fragments.transport

import com.caturindo.models.RoomDto
import com.caturindo.models.TransportDto


interface TransportContract {
    interface View {
        fun showProgress()
        fun hideProgress()
        fun onSuccessGet(data : MutableList<TransportDto>)
        fun onErrorGetData(msg: String?)
    }

    interface Presenter {
        fun getTransport(starTime : String, endTime : String, date : String)
        fun getAllTransport()

    }
}