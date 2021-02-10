package com.caturindo.activities.meeting.detail

import com.caturindo.activities.meeting.create.model.UploadDto
import com.caturindo.activities.meeting.model.AddMeetingCommentRequest
import com.caturindo.activities.meeting.model.AddSubMeetingCommentRequest
import com.caturindo.activities.task.detail.model.AddCommentRequest
import com.caturindo.activities.task.detail.model.CommentDto
import com.caturindo.models.CancelMeetingRequest
import com.caturindo.models.MeetingRequest
import com.caturindo.models.RegisterDto
import com.caturindo.models.TaskRequest
import okhttp3.MultipartBody
import java.io.File


interface SubCommentMeetingContract {
    interface View {
        fun showProgress()
        fun hideProgress()
        fun successData(data : ArrayList<CommentDto>)
        fun failGetComment (msg : String)
        fun successPostComment(msg: String)
        fun failPostComment(msg: String)
        fun succsesCancel(msg: String)




    }

    interface Presenter {
        fun updateMeeting(idMeeting : String)
        fun cancelMeeting(idMeeting : String)
        fun getComment(idMeeting : String)
        fun postComment(addCommentRequest: AddSubMeetingCommentRequest)

    }
}