package com.caturindo.models

import com.google.gson.annotations.SerializedName

data class CancelMeetingRequest(

	@field:SerializedName("id_meeting")
	val idMeeting: String? = null
)
