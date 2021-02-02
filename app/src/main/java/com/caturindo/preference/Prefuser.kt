package com.caturindo.preference

import com.caturindo.activities.meeting.model.AddMemberMeetingDto
import com.caturindo.activities.task.add_team.model.AddMemberTaskDto
import com.caturindo.constant.Constant
import com.caturindo.models.BookingRequest
import com.caturindo.models.RegisterDto
import com.caturindo.models.UserDto
import com.orhanobut.hawk.Hawk

class Prefuser {
    fun setUser(user: RegisterDto?) = Hawk.put(Constant.USER_PREF, user)

    fun getUser(): RegisterDto? = Hawk.get(Constant.USER_PREF, null)

    fun setAddTeam(userDto: ArrayList<UserDto>?) = Hawk.put(UserDto::class.java.simpleName, userDto)

    fun getAddTeam(): ArrayList<UserDto>? = Hawk.get(UserDto::class.java.simpleName, null)

    //for room click 1 can booking, 0 cant booking
    fun setvalidateOnclickRoom(validate: String?) = Hawk.put("VALIDATE", validate)

    fun getvalidateOnclickRoom(): String? = Hawk.get("VALIDATE", null)


    //for meeting =1 / submeeting = 0
    fun setvalidateMeeting(validate: String?) = Hawk.put("MEETING", validate)

    fun getvalidateMeeting(): String? = Hawk.get("MEETING", null)


  fun setIdPerentMeeting(validate: String?) = Hawk.put("ID_MEETING", validate)

    fun getIdParentMeeting(): String? = Hawk.get("ID_MEETING", null)




    fun setBooking(data : BookingRequest) = Hawk.put(BookingRequest::class.java.simpleName,data)

    fun getBooking() : BookingRequest? = Hawk.get(BookingRequest::class.java.simpleName, null)

    fun setDateBooking (data : ModelDate?)  = Hawk.put(ModelDate::class.java.simpleName,data)

    fun getDateBooking() : ModelDate? = Hawk.get(ModelDate::class.java.simpleName, null)

    fun setPropertyBooking (data : ModelBooking?)  = Hawk.put(ModelBooking::class.java.simpleName,data)

    fun getPropertyBooking() : ModelBooking? = Hawk.get(ModelBooking::class.java.simpleName, null)

    fun setPropertyMeeting (data : ModelMeeting?)  = Hawk.put(ModelMeeting::class.java.simpleName,data)

    fun getPropertyMeeting() : ModelMeeting? = Hawk.get(ModelMeeting::class.java.simpleName, null)

    fun setTeamTask (data : ArrayList<AddMemberTaskDto>?)  = Hawk.put(AddMemberTaskDto::class.java.simpleName,data)

    fun getTeamTask() :  ArrayList<AddMemberTaskDto>? = Hawk.get(AddMemberTaskDto::class.java.simpleName, null)

    fun setTeamMeeting (data : ArrayList<AddMemberMeetingDto>?)  = Hawk.put(AddMemberMeetingDto::class.java.simpleName,data)

    fun getTeamMeeting() :  ArrayList<AddMemberMeetingDto>? = Hawk.get(AddMemberMeetingDto::class.java.simpleName, null)



}

class ModelUserTask(
        val name : String? =null,
        val id : String? = null
)

class ModelDate (
        val starTime :String? = null,
        val endTime :String? = null,
        val date :String? = null
)

class ModelBooking(
        val codeTransport : String? = null,
        val codeRoom : String? = null,
        val driverName : String? = null,
        val location : String? = null,
        val note : String


)

class ModelMeeting(
        val idFile: String? =null,
        val title: String? =null,
        val desc: String? =null,
        val url : String? = null
)