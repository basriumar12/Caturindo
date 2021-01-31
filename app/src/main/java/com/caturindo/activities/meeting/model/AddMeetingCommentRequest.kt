package com.caturindo.activities.meeting.model

import com.google.gson.annotations.SerializedName

class AddMeetingCommentRequest (

    @field:SerializedName("id_meeting")
    val idTask: String? = null,

    @field:SerializedName("comment")
    val comment: String? = null,

    @field:SerializedName("id_user")
    val idUser: String? = null
)