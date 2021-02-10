package com.caturindo.activities.meeting.model

import com.google.gson.annotations.SerializedName

class AddSubMeetingCommentRequest (

    @field:SerializedName("id_sub_meeting")
    val idTask: String? = null,

    @field:SerializedName("comment")
    val comment: String? = null,

    @field:SerializedName("id_user")
    val idUser: String? = null
)