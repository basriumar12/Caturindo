package com.caturindo.activities.task.add_team.model

import com.google.gson.annotations.SerializedName

data class AddMemberTaskDto(

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
