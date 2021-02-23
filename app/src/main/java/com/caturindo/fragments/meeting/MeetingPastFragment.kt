package com.caturindo.fragments.meeting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caturindo.BaseFragment
import com.caturindo.R
import com.caturindo.activities.meeting.detail.MeetingDetailActivity
import com.caturindo.activities.meeting.model.MeetingNewRequest
import com.caturindo.adapters.MeetingItemAdapter
import com.caturindo.models.MeetingDtoNew
import com.caturindo.models.MeetingModel
import com.caturindo.preference.Prefuser
import kotlinx.android.synthetic.main.fragment_room_available.*
import kotlinx.android.synthetic.main.fragment_search_bar.*
import java.text.SimpleDateFormat
import java.util.*

class MeetingPastFragment : BaseFragment(), MeetingContract.View, AdapterPastMeeting.OnListener {
    private var rootView: View? = null
    private var rvMeeting: RecyclerView? = null
    private val adapter: MeetingItemAdapter? = null
    private lateinit var presenter : MeetingPresenter
    private  var progress_circular : ProgressBar? =null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_meeting_current, null)
        rvMeeting = rootView?.findViewById(R.id.rv_meeting)
        progress_circular = rootView?.findViewById(R.id.progress_circular)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = MeetingPresenter(this)
        val sdf = SimpleDateFormat("yyyy-M")
        var currentDate = sdf.format(Date())
        if (currentDate.isNullOrEmpty()){
            currentDate = ""
        }
        Prefuser().setCarruntDate(currentDate)

    }

    override fun onResume() {
        super.onResume()


        presenter.getMeetingAll(MeetingNewRequest(
                "", Prefuser().getUser()?.id.toString(),"1",Prefuser().getCarruntDate()
        ))
    }

    override fun showProgress() {
        progress_circular?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress_circular?.visibility = View.GONE
    }

    override fun onSuccessGet(data: MutableList<MeetingDtoNew>) {
        if (data.isNullOrEmpty()){
            paren_data_empty.visibility = View.VISIBLE
            rvMeeting?.visibility = View.GONE
        }else{
            rvMeeting?.visibility = View.VISIBLE
            paren_data_empty.visibility = View.GONE
        }

        val adapterMeeting = rootView?.context?.let { AdapterPastMeeting(it,data,this) }
        rvMeeting?.layoutManager = LinearLayoutManager(rootView?.context)
        rvMeeting?.adapter = adapterMeeting
        adapterMeeting?.notifyDataSetChanged()
        search_text.setOnQueryTextListener(object :
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterMeeting?.filter?.filter(newText)
                return false
            }

        })

    }

    override fun onErrorGetData(msg: String?) {
        showLongErrorMessage(msg)

    }

    override fun dataEmpty() {
        paren_data_empty.visibility = View.VISIBLE
        rvMeeting?.visibility = View.GONE

    }

    override fun onClick(data: MeetingDtoNew) {

        startActivity(Intent(activity, MeetingDetailActivity::class.java)
                .putExtra(MeetingDtoNew::class.java.simpleName, data))
    }
}