package com.caturindo.fragments.task

import com.caturindo.activities.task.model.TaskNewRequest
import com.caturindo.models.RegisterDto
import com.caturindo.models.TaskDto
import com.caturindo.models.UpdateTokenRequest


interface TaskContract {
    interface View {
        fun showProgress()
        fun hideProgress()
        fun onSuccessGet(data : MutableList<TaskDto>)
        fun onErrorGetData(msg: String?)
        fun dataEmpty()

    }

    interface Presenter {
        fun getTask()
        fun getAllTask(body:TaskNewRequest)
        fun getUpdateToken(body : UpdateTokenRequest)

    }
}