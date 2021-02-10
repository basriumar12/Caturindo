package com.caturindo.fragments.meeting

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.caturindo.R
import com.caturindo.models.DataSubMeetingItemItem
import com.caturindo.models.MeetingDtoNew
import kotlinx.android.synthetic.main.item_meeting_indoor.view.*
import kotlinx.android.synthetic.main.item_room.view.*

class AdapterPastMeeting(val context: Context, val data: MutableList<MeetingDtoNew>,
                         private val itemListiner: OnListener
                    ) :
    RecyclerView.Adapter<AdapterPastMeeting.ViewHolder>() {
    companion object {
        const val ON_CLICK_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_meeting_indoor, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }
    interface OnListener {
        fun onClick(data: MeetingDtoNew)
    }
    @SuppressLint("WrongConstant")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.rv_sub_meeting.apply {
            layoutManager = LinearLayoutManager(holder.itemView.rv_sub_meeting.context, LinearLayout.VERTICAL, false)
            adapter = AdapterSubMeeting(context,
                    data.get(position).dataSubMeeting as MutableList<DataSubMeetingItemItem>
            )
        }
        data?.get(position).let { data ->
            holder.bindView(data,itemListiner)
            holder.itemView.tv_meeting_title.text = data.title +" - "+data.id
            holder.itemView.tv_meeting_desc.text = data.description
            holder.itemView.tv_meeting_date.text = data.date
            holder.itemView.tv_meeting_time.text = data.time
            holder.itemView.tv_meeting_loc.text = data.location

            if (!data.countMembers.toString().isNullOrEmpty()){
                holder.itemView.tv_meeting_participant.text = data.countMembers.toString()
            }else{
                holder.itemView.tv_meeting_participant.text = "0"
            }

        }
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bindView(data: MeetingDtoNew, listiner: OnListener){
            itemView.setOnClickListener {
                listiner.onClick(data)
            }


        }

    }


}