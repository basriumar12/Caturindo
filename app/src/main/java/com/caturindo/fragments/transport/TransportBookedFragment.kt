package com.caturindo.fragments.transport

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caturindo.BaseFragment
import com.caturindo.R
import com.caturindo.activities.transport.TransportDetailActivity
import com.caturindo.adapters.TransportItemAdapter
import com.caturindo.models.TransportDto
import com.caturindo.models.TransportItemModel
import kotlinx.android.synthetic.main.fragment_room_booked.paren_data_empty
import kotlinx.android.synthetic.main.fragment_room_booked.progress_circular
import java.util.*

class TransportBookedFragment : BaseFragment(), TransportContract.View, AdapterTransport.OnListener {
    private var rootView: View? = null
    private val adapter: TransportItemAdapter? = null
    private var rvTransport: RecyclerView? = null
    private val itemlist: ArrayList<TransportItemModel>? = null
    private lateinit var presenter : TransportPresenter
    private var keterangan = ""
    private var valueTimeStart = ""
    private var valueTimeEnd = ""
    private var valueDate = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_transport_booked, null)
        rvTransport = rootView?.findViewById(R.id.rvTransportAvailable)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = TransportPresenter(this)
        presenter.getAllTransport()

    }


    override fun showProgress() {
        progress_circular.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress_circular.visibility = View.GONE
    }

    override fun onSuccessGet(data: MutableList<TransportDto>) {
        rvTransport?.layoutManager = GridLayoutManager(rootView?.context, 2)
        val adapterRoom = rootView?.context?.let { AdapterTransport(it, data, this) }
        rvTransport?.adapter = adapterRoom
        rvTransport?.setHasFixedSize(true)
        adapterRoom?.notifyDataSetChanged()
        if (data.size == 0 || data == null) {
            paren_data_empty?.visibility = View.VISIBLE
        }else{
            paren_data_empty?.visibility = View.GONE
        }
    }

    override fun onErrorGetData(msg: String?) {
        showLongErrorMessage(msg)
    }

    override fun onClick(data: TransportDto) {
        startActivity(Intent(rootView?.context, TransportDetailActivity::class.java)
                .putExtra(TransportDto::class.java.simpleName, data)
        )
    }
}