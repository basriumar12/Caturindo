package com.caturindo.activities.team.model

import com.google.gson.annotations.SerializedName

data class AddTeamRequest(

	@field:SerializedName("id_member")
	val idMember: String? = null,

	@field:SerializedName("id_user")
	val idUser: String? = null
)
