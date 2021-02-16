package com.caturindo.activities.task.detail

import com.caturindo.activities.meeting.create.model.UploadDto
import com.caturindo.activities.task.detail.model.AddCommentRequest
import com.caturindo.activities.task.detail.model.CommentDto
import com.caturindo.models.CancelMeetingRequest
import com.caturindo.models.MeetingRequest
import com.caturindo.models.RegisterDto
import com.caturindo.models.TaskRequest
import okhttp3.MultipartBody
import java.io.File


interface CommentTaskContract {
    interface View {
        fun showProgress()
        fun hideProgress()
        fun successData(data : ArrayList<CommentDto>)
        fun failGetComment (msg : String)
        fun successPostComment(msg: String)
        fun failPostComment(msg: String)


    }

    interface Presenter {
        fun getComment(idTask : String)
        fun postCancel(idTask : String)
        fun postDone(idTask : String)
        fun postComment(addCommentRequest: AddCommentRequest)

    }
}