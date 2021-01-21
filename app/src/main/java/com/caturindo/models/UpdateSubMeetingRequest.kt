package com.caturindo.models

import com.google.gson.annotations.SerializedName

data class UpdateSubMeetingRequest(

	@field:SerializedName("id_sub_meeting")
	val idMeeting: String? = null
)
