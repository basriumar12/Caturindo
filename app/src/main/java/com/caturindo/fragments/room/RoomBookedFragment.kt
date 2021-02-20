package com.caturindo.fragments.room

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caturindo.BaseFragment
import com.caturindo.R
import com.caturindo.activities.room.RoomDetailActivity
import com.caturindo.adapters.RoomItemAdapter
import com.caturindo.models.RoomDto
import com.caturindo.models.RoomItemModel
import java.util.*

class RoomBookedFragment : BaseFragment(), AdapterRoom.OnListener, RoomContract.View {
    private var presenter: RoomPresenter? = null
    private var rootView: View? = null
    private val adapter: RoomItemAdapter? = null
    private var rvRoom: RecyclerView? = null
    private var progress_circular: ProgressBar? = null
    private val itemlist: ArrayList<RoomItemModel>? = null
    private var linearLayout: LinearLayoutCompat? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_room_booked, null)
        rvRoom = rootView?.findViewById(R.id.rvRoomBooked)
        linearLayout = rootView?.findViewById(R.id.paren_data_empty)
        progress_circular = rootView?.findViewById(R.id.progress_circular)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = RoomPresenter(this)
        presenter?.getAllRoom()
    }

    override fun onClick(data: RoomDto) {
        startActivity(Intent(rootView?.context, RoomDetailActivity::class.java)
                .putExtra(RoomDto::class.java.simpleName, data))
    }

    override fun showProgress() {
        progress_circular?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress_circular?.visibility = View.GONE
    }

    override fun onSuccessGet(data: MutableList<RoomDto>) {
        rvRoom?.layoutManager = GridLayoutManager(activity, 2)
        val adapterRoom = rootView?.context?.let { AdapterRoom(it, data, this) }
        rvRoom?.adapter = adapterRoom
        rvRoom?.setHasFixedSize(true)
        adapterRoom?.notifyDataSetChanged()
        if (data.size == 0 || data == null) {
            linearLayout?.visibility = View.VISIBLE
        }
    }

    override fun onErrorGetData(msg: String?) {
        showLongErrorMessage(msg)
    }
}