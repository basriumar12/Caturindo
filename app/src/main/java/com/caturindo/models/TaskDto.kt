package com.caturindo.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TaskDto(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("name_task")
	val nameTask: String? = null,

	@field:SerializedName("member")
	val member: Any? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: String? = null
) : Serializable
