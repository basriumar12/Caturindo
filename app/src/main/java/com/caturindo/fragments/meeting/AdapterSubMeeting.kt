package com.caturindo.fragments.meeting

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.caturindo.R
import com.caturindo.activities.meeting.detail.MeetingDetailActivity
import com.caturindo.activities.meeting.detail.SubMeetingDetailActivity
import com.caturindo.models.DataSubMeetingItemItem
import com.caturindo.models.MeetingDtoNew
import kotlinx.android.synthetic.main.item_meeting_outdoor.view.*
import kotlinx.android.synthetic.main.item_room.view.*

class AdapterSubMeeting(val context: Context, val data: MutableList<DataSubMeetingItemItem>
                    ) :
    RecyclerView.Adapter<AdapterSubMeeting.ViewHolder>() {
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
        fun onClick(data: DataSubMeetingItemItem)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        data?.get(position).let { data ->

            holder.itemView.setOnClickListener {

                holder.itemView.context.startActivity(Intent(holder.itemView.context, SubMeetingDetailActivity::class.java)
                        .putExtra(DataSubMeetingItemItem::class.java.simpleName, data))

            }

            holder.itemView.tv_meeting_title.text = data.title +" - "+data.id
            holder.itemView.tv_meeting_desc.text = data.description
            holder.itemView.tv_meeting_date.text = data.date
            holder.itemView.tv_meeting_time.text = data.time
            holder.itemView.tv_meeting_loc.text = data.location
            holder.itemView.tv_meeting_participant.text = ""

        }
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bindView(data: DataSubMeetingItemItem){



        }

    }


}