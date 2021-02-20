package com.caturindo.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TaskDto(

        @field:SerializedName("due_date")
        val date: String? = null,
        @field:SerializedName("id_user")
        val id_user: String? = null,

        @field:SerializedName("name_task")
        val nameTask: String? = null,

        @field:SerializedName("member")
        val member: List<String>? = null,

        @field:SerializedName("description")
        val description: String? = null,
        @field:SerializedName("count_members")
        val count_members: Int? = 0,

		@field:SerializedName("status_task")
        val statusTask: String? = null,

        @field:SerializedName("id")
        val id: String? = null,
        @field:SerializedName("time")
        val time: String? = null,
        @field:SerializedName("file")
        val file: String? = null
) : Serializable
