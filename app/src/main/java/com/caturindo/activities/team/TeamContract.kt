package com.caturindo.activities.team

import com.caturindo.activities.team.model.AddTeamRequest
import com.caturindo.activities.team.model.MemberItem
import com.caturindo.models.RegisterDto
import com.caturindo.models.UserDto
import com.caturindo.models.UserDtoNew


interface TeamContract {
    interface View {
        fun showProgress()
        fun hideProgress()

        fun onSuccessGetDetail(data : UserDtoNew)
        fun onErrorGetDataDetail(msg: String?)

        fun onSuccessGet(data : List<UserDto>)
        fun onSuccessGetTeam(data : List<MemberItem>)

        fun onErrorGetData(msg: String?)

        fun onSuccessAddTeam(msg: String?)

        fun dataEmpty()
    }

    interface Presenter {
        fun getUser(idUser : String)
        fun getUser()
        fun postTeam(data : AddTeamRequest)
        fun deleteTeam(data : AddTeamRequest)
        fun getTeamMember(idUser : String)


    }
}