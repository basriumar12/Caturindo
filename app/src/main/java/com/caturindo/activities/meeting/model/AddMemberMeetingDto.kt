package com.caturindo.activities.meeting.model

import com.google.gson.annotations.SerializedName

data class AddMemberMeetingDto(

	@field:SerializedName("id_booking")
	val idBooking: String? = null,

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("sub_meting")
	val subMeting: Any? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("id_user")
	val idUser: String? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("tag")
	val tag: String? = null,

	@field:SerializedName("status_meeting")
	val statusMeeting: Any? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("id_file")
	val idFile: String? = null
)
