package com.caturindo.activities.meeting.model

import com.google.gson.annotations.SerializedName

data class MeetingNewRequest(

	@field:SerializedName("id_meeting")
	val idMeeting: String? = null,

	@field:SerializedName("id_user")
	val idUser: String? = null,

	@field:SerializedName("status_meeting")
	val statusMeeting: String? = null,

	@field:SerializedName("tanggal")
	val tanggal: String? = null
)
