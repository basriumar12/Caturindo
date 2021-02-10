package com.caturindo.activities.notif.model

import com.google.gson.annotations.SerializedName

data class NotifDto(

	@field:SerializedName("task")
	val task: Task? = null,

	@field:SerializedName("id_meeting")
	val idMeeting: String? = null,

	@field:SerializedName("id_task")
	val idTask: Any? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("id_user")
	val idUser: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("meeting")
	val meeting: Meeting? = null,

	@field:SerializedName("id_user_tag")
	val idUserTag: String? = null
)

data class Meeting(

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)

data class Task(

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)

