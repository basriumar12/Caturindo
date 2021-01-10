package com.caturindo.fragments.task

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caturindo.R
import com.caturindo.models.TaskDto
import kotlinx.android.synthetic.main.item_task.view.*

class AdapterTask(val context: Context, val data: MutableList<TaskDto>,
                  private val itemListiner: OnListener
                    ) :
    RecyclerView.Adapter<AdapterTask.ViewHolder>() {
    companion object {
        const val ON_CLICK_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }
    interface OnListener {
        fun onClick(data: TaskDto)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        data?.get(position).let { data ->
            holder.bindView(data,itemListiner)
            holder.itemView.tv_task_name.text = data.nameTask.toString()
            holder.itemView.tv_task_desc.text = data.description.toString()
            holder.itemView.tv_task_date.text = data.date.toString()
            if (data.member?.toString().equals(null)){
                holder.itemView.tv_task_participant_count.text = "0"
            }else {
                holder.itemView.tv_task_participant_count.text = data.member.toString()
            }

            val index = position % 2
            if (index == 0) {
                holder.itemView.setBackgroundColor(
                    context.resources.getColor(android.R.color.white)
                )
            } else if (index == 1) {
                holder.itemView.setBackgroundColor(
                    context.resources.getColor(R.color.colorPrimary)
                )

            }
        }
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bindView(data: TaskDto, listiner: OnListener){
            itemView.setOnClickListener {
                listiner.onClick(data)
            }


        }

    }


}