package com.caturindo.models

import com.google.gson.annotations.SerializedName

data class TaskRequest(

	@field:SerializedName("name_task")
	val nameTask: String? = null,

	@field:SerializedName("id_meeting")
	val idMeeting: String? = null,

	@field:SerializedName("due_date")
	val dueDate: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id_user")
	val idUser: String? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("id_file")
	val idFile: String? = null,

	@field:SerializedName("id_group")
	val id_group: String? = null
)
