package com.caturindo.fragments.meeting

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.caturindo.R
import com.caturindo.models.DataSubMeetingItemItem
import com.caturindo.models.MeetingDtoNew
import kotlinx.android.synthetic.main.item_meeting_outdoor.view.*
import kotlinx.android.synthetic.main.item_room.view.*
import java.util.*
import kotlin.collections.ArrayList

class AdapterMeeting(val context: Context, val data: MutableList<MeetingDtoNew>,
                     private val itemListiner: OnListener
                    ) :
    RecyclerView.Adapter<AdapterMeeting.ViewHolder>(), Filterable {
    var dataFilterList = ArrayList<MeetingDtoNew>()
    init {
        dataFilterList = data as ArrayList<MeetingDtoNew>
    }
    companion object {
        const val ON_CLICK_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_meeting_outdoor, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return dataFilterList.size
    }
    interface OnListener {
        fun onClick(data: MeetingDtoNew)
    }
    @SuppressLint("WrongConstant")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val datas = dataFilterList[position]
        holder.itemView.rv_sub_meeting.apply {
            try {
                if (!data.get(position).dataSubMeeting.isNullOrEmpty())
                layoutManager = LinearLayoutManager(holder.itemView.rv_sub_meeting.context, LinearLayout.VERTICAL, false)
                adapter = AdapterSubMeeting(context,
                        data.get(position).dataSubMeeting as MutableList<DataSubMeetingItemItem>
                )
            }catch (e: TypeCastException){

            }catch (e : NullPointerException){

            }
        }
        datas?.let { data ->
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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    dataFilterList = data as ArrayList<MeetingDtoNew>
                } else {
                    val resultList = ArrayList<MeetingDtoNew>()
                    for (row in data) {
                        if (row.title?.toString()?.toLowerCase(Locale.ROOT)?.contains(charSearch.toLowerCase(Locale.ROOT))!!) {
                            resultList.add(row)
                        }
                    }
                    dataFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = dataFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                dataFilterList = results?.values as ArrayList<MeetingDtoNew>
                notifyDataSetChanged()
            }

        }
    }


}