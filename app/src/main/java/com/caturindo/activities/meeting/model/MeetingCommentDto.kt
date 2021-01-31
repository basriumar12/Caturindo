package com.caturindo.activities.meeting.model

import com.google.gson.annotations.SerializedName

data class MeetingCommentDto(

	@field:SerializedName("id_meeting")
	val idMeeting: String? = null,

	@field:SerializedName("comment")
	val comment: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("id_user")
	val idUser: String? = null
)
