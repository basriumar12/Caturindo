package com.caturindo.fragments.meeting

import com.caturindo.models.MeetingDtoNew
import com.caturindo.models.RoomDto


interface MeetingContract {
    interface View {
        fun showProgress()
        fun hideProgress()
        fun onSuccessGet(data : MutableList<MeetingDtoNew>)
        fun onErrorGetData(msg: String?)
        fun dataEmpty()
    }

    interface Presenter {
        fun getMeeting(status : String)

    }
}