package com.caturindo.activities.notif

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.caturindo.R
import com.caturindo.activities.notif.model.NotifDto
import com.caturindo.models.UserDto
import kotlinx.android.synthetic.main.item_add_team.view.*
import kotlinx.android.synthetic.main.item_notif.view.*
import kotlinx.android.synthetic.main.item_team_list.view.*
import java.util.*
import kotlin.collections.ArrayList

class AdapterNotif(val context: Context, val data: MutableList<NotifDto>,
                   private val itemListiner: OnListener
) :
        RecyclerView.Adapter<AdapterNotif.ViewHolder>(), Filterable {

    var dataFilterList = ArrayList<NotifDto>()

    init {
        dataFilterList = data as ArrayList<NotifDto>
    }

    companion object {
        const val ON_CLICK_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_notif, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return dataFilterList.size
    }

    interface OnListener {
        fun onClick(data: NotifDto)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val datas = dataFilterList[position]
        holder.bindView(datas, itemListiner)

        holder.itemView.tv_notification_title.text = dataFilterList.get(position).title

        if (dataFilterList.get(position).meeting != null){

            holder.itemView.meeting.visibility = View.VISIBLE
            holder.itemView.task.visibility = View.GONE
            val data = dataFilterList.get(position)?.meeting
            holder.itemView.tv_meeting_title.text = data?.title.toString()
            holder.itemView.tv_meeting_content.text = data?.description.toString()
        }

        if (dataFilterList.get(position).task != null){
            holder.itemView.meeting.visibility = View.GONE
            holder.itemView.task.visibility = View.VISIBLE
            val data = dataFilterList.get(position)?.task
            holder.itemView.tv_task_title.text = data?.title.toString()
            holder.itemView.tv_task_content.text = data?.description.toString()
        }

    }

    class ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {

        fun bindView(data: NotifDto, listiner: OnListener) {

        
        }
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    dataFilterList = data as ArrayList<NotifDto>
                } else {
                    val resultList = ArrayList<NotifDto>()
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
                dataFilterList = results?.values as ArrayList<NotifDto>
                notifyDataSetChanged()
            }

        }
    }


}