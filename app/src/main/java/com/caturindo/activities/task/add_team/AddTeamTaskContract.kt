package com.caturindo.activities.task.add_team

import com.caturindo.activities.task.add_team.model.AddMemberTaskDto
import com.caturindo.models.RegisterDto
import com.caturindo.models.UserDto


interface AddTeamTaskContract {
    interface View {
        fun showProgress()
        fun hideProgress()
        fun onSuccessGet(data : List<UserDto>)
        fun onErrorGetData(msg: String?)
        fun suksesAddMember(msg : String, data : AddMemberTaskDto)
        fun faildAddMember(msg: String)
    }

    interface Presenter {
        fun getUser()
        fun addTeam(idUser : String, idTask : String)


    }
}