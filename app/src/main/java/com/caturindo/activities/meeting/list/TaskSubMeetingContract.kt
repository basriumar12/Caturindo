package com.caturindo.activities.meeting.list

import com.caturindo.models.MeetingDtoNew
import com.caturindo.models.MeetingSubDtoNew
import com.caturindo.models.RoomDto


interface TaskSubMeetingContract {
    interface View {
        fun showProgress()
        fun hideProgress()
        fun onSuccessGet(data : MutableList<MeetingSubDtoNew>)
        fun onErrorGetData(msg: String?)
    }

    interface Presenter {
        fun getMeeting(status : String)

    }
}