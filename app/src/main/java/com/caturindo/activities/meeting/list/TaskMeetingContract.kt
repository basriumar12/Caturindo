package com.caturindo.activities.meeting.list

import com.caturindo.models.MeetingDtoNew
import com.caturindo.models.RoomDto


interface TaskMeetingContract {
    interface View {
        fun showProgress()
        fun hideProgress()
        fun onSuccessGet(data : MutableList<MeetingDtoNew>)
        fun onErrorGetData(msg: String?)
    }

    interface Presenter {
        fun getMeeting(status : String)

    }
}