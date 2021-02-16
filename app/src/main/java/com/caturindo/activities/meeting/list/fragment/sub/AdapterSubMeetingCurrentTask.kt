package com.caturindo.activities.meeting.list.fragment.sub

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caturindo.R
import com.caturindo.models.MeetingSubDtoNew
import kotlinx.android.synthetic.main.item_meeting_outdoor.view.*

class AdapterSubMeetingCurrentTask(val context: Context, val data: MutableList<MeetingSubDtoNew>,
                                   private val itemListiner: OnListener
                    ) :
    RecyclerView.Adapter<AdapterSubMeetingCurrentTask.ViewHolder>() {
    companion object {
        const val ON_CLICK_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_meeting_outdoor, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }
    interface OnListener {
        fun onClick(data: MeetingSubDtoNew)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        data?.get(position).let { data ->
            holder.bindView(data,itemListiner)

            holder.itemView.tv_meeting_title.text = data.title +" - "+data.id
            holder.itemView.tv_meeting_desc.text = data.description
            holder.itemView.tv_meeting_date.text = data.date
            holder.itemView.tv_meeting_time.text = data.time
            holder.itemView.tv_meeting_loc.text = data.location
        }
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bindView(data: MeetingSubDtoNew, listiner: OnListener){
            itemView.setOnClickListener {
                listiner.onClick(data)
            }


        }

    }


}