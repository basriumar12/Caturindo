package com.caturindo.activities.meeting.list.fragment.sub

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caturindo.BaseFragment
import com.caturindo.R
import com.caturindo.activities.meeting.detail.MeetingDetailActivity
import com.caturindo.activities.meeting.list.AdapterSubMeetingTask
import com.caturindo.activities.meeting.list.TaskSubMeetingContract
import com.caturindo.activities.meeting.list.TaskSubMeetingPresenter
import com.caturindo.activities.task.CreateTaskActivity
import com.caturindo.activities.task.detail.TaskDetailActivity
import com.caturindo.adapters.MeetingItemAdapter
import com.caturindo.fragments.meeting.AdapterMeeting
import com.caturindo.fragments.meeting.MeetingContract
import com.caturindo.fragments.meeting.MeetingPresenter
import com.caturindo.models.MeetingDtoNew
import com.caturindo.models.MeetingSubDtoNew
import kotlinx.android.synthetic.main.activity_list_sub_meeting.*
import kotlinx.android.synthetic.main.fragment_meeting_current.*
import kotlinx.android.synthetic.main.fragment_meeting_current.paren_data_empty
import kotlinx.android.synthetic.main.fragment_meeting_current.progress_circular
import kotlinx.android.synthetic.main.fragment_meeting_current.rv_meeting
import kotlinx.android.synthetic.main.fragment_room_available.*
import kotlinx.android.synthetic.main.fragment_search_bar.*

class SubMeetingCurrentTaskFragment : BaseFragment(), TaskSubMeetingContract.View, AdapterSubMeetingCurrentTask.OnListener {
    private var rootView: View? = null
    private var rvMeeting: RecyclerView? = null
    private val adapter: MeetingItemAdapter? = null
    private lateinit var presenter : TaskSubMeetingPresenter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_meeting_current, null)
        rvMeeting = rootView?.findViewById(R.id.rv_meeting)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = TaskSubMeetingPresenter(this)
        parent_search.visibility = View.GONE

    }

    override fun onResume() {
        super.onResume()
        presenter.getMeeting("0")
    }

    override fun showProgress() {
        progress_circular.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress_circular.visibility = View.GONE
    }

    override fun onSuccessGet(data: MutableList<MeetingSubDtoNew>) {
        if (data.isNullOrEmpty()){
            paren_data_empty.visibility = View.VISIBLE
        }else{
            paren_data_empty.visibility = View.GONE
        }

        val adapterMeetingTask = rootView?.context?.let { AdapterSubMeetingCurrentTask(it,data,this) }
        rvMeeting?.layoutManager = LinearLayoutManager(rootView?.context)
        rvMeeting?.adapter = adapterMeetingTask
        adapterMeetingTask?.notifyDataSetChanged()
    }


    override fun onErrorGetData(msg: String?) {
        showLongErrorMessage(msg)
        
    }




    override fun onClick(data: MeetingSubDtoNew) {
        startActivity(Intent(rootView?.context, CreateTaskActivity::class.java)
                .putExtra("ID",data.id)
                .putExtra("TITLE",data.title)
        )
    }
}