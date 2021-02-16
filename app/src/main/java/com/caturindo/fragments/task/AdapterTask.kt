package com.caturindo.fragments.task

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.caturindo.R
import com.caturindo.models.TaskDto
import com.caturindo.models.UserDto
import kotlinx.android.synthetic.main.item_task.view.*
import java.util.*
import kotlin.collections.ArrayList

class AdapterTask(val context: Context, val data: MutableList<TaskDto>,
                  private val itemListiner: OnListener
) :
        RecyclerView.Adapter<AdapterTask.ViewHolder>(), Filterable {
    var dataFilterList = ArrayList<TaskDto>()

    init {
        dataFilterList = data as ArrayList<TaskDto>
    }

    companion object {
        const val ON_CLICK_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return dataFilterList.size
    }

    interface OnListener {
        fun onClick(data: TaskDto)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val datas = dataFilterList[position]
        holder.bindView(datas, itemListiner)

        if (datas.statusTask.equals("Y")) {
            holder.itemView.cv_task.setBackgroundColor(holder.itemView.context.resources.getColor(R.color.black))
        }


    }

    class ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {

        fun bindView(data: TaskDto, listiner: OnListener) {
            itemView.setOnClickListener {
                listiner.onClick(data)
            }

            itemView.tv_task_name.text = data.nameTask.toString() + " - " + data.id
            itemView.tv_task_desc.text = data.description.toString()
            itemView.tv_task_date.text = data.time + "\n" + data.date.toString()
            itemView.tv_task_participant_count.text = "0"

            if (data.member.isNullOrEmpty()) {
                itemView.tv_task_participant_count.text = "0"
            } else {
                itemView.tv_task_participant_count.text = data.member.size.toString()
            }


        }

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    dataFilterList = data as ArrayList<TaskDto>
                } else {
                    val resultList = ArrayList<TaskDto>()
                    for (row in data) {
                        if (row.nameTask?.toString()?.toLowerCase(Locale.ROOT)?.contains(charSearch.toLowerCase(Locale.ROOT))!!) {
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
                dataFilterList = results?.values as ArrayList<TaskDto>
                notifyDataSetChanged()
            }

        }
    }


}