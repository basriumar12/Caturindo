package com.caturindo.activities.meeting.add_team

import com.caturindo.activities.meeting.model.AddMemberMeetingDto
import com.caturindo.activities.task.add_team.model.AddMemberTaskDto
import com.caturindo.models.RegisterDto
import com.caturindo.models.UserDto


interface AddTeamMeetingContract {
    interface View {
        fun showProgress()
        fun hideProgress()
        fun onSuccessGet(data : List<UserDto>)
        fun onErrorGetData(msg: String?)
        fun suksesAddMember(msg : String, data : AddMemberMeetingDto)
        fun faildAddMember(msg: String)
    }

    interface Presenter {
        fun getUser()
        fun addTeam(username : String, idMeeting : String)


    }
}