package com.caturindo.activities.notif

import com.caturindo.activities.meeting.create.model.UploadDto
import com.caturindo.activities.meeting.model.AddMeetingCommentRequest
import com.caturindo.activities.notif.model.NotifDto
import com.caturindo.activities.task.detail.model.AddCommentRequest
import com.caturindo.activities.task.detail.model.CommentDto
import com.caturindo.models.CancelMeetingRequest
import com.caturindo.models.MeetingRequest
import com.caturindo.models.RegisterDto
import com.caturindo.models.TaskRequest
import okhttp3.MultipartBody
import java.io.File


interface NotifContract {
    interface View {
        fun showProgress()
        fun hideProgress()
        fun successData(data : ArrayList<NotifDto>)
        fun failt (msg : String)
        fun dataEmpty()





    }

    interface Presenter {
        fun getNotif(idUser : String)

    }
}