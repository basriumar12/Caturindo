package com.caturindo.models

import com.google.gson.annotations.SerializedName

data class MeetingSubDtoNew(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("code_transport")
	val codeTransport: String? = null,

	@field:SerializedName("time_start")
	val timeStart: Any? = null,

	@field:SerializedName("id_meeting")
	val idMeeting: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: Any? = null,

	@field:SerializedName("id_task")
	val idTask: Any? = null,

	@field:SerializedName("id_user")
	val idUser: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("id_booking")
	val idBooking: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: Any? = null,

	@field:SerializedName("max_people")
	val maxPeople: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("tag")
	val tag: String? = null,

	@field:SerializedName("time_end")
	val timeEnd: Any? = null,

	@field:SerializedName("status_meeting")
	val statusMeeting: String? = null,

	@field:SerializedName("id_file")
	val idFile: List<String>? = null,
	@field:SerializedName("file")
	val file: List<String>? = null,

	@field:SerializedName("code_room")
	val codeRoom: String? = null
)
