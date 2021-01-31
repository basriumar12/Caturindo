package com.caturindo.activities.task.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.caturindo.R
import com.caturindo.activities.task.detail.model.CommentDto
import kotlinx.android.synthetic.main.item_add_team.view.*
import kotlinx.android.synthetic.main.item_add_team.view.tv_user_name
import kotlinx.android.synthetic.main.item_comment.view.*
import java.util.*
import kotlin.collections.ArrayList

class AdapterComment(val context: Context, val data: MutableList<CommentDto>,
                     private val itemListiner: OnListener
) :
        RecyclerView.Adapter<AdapterComment.ViewHolder>(), Filterable {

    var dataFilterList = ArrayList<CommentDto>()

    init {
        dataFilterList = data as ArrayList<CommentDto>
    }

    companion object {
        const val ON_CLICK_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return dataFilterList.size
    }

    interface OnListener {
        fun onClick(data: CommentDto)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val datas = dataFilterList[position]
        holder.bindView(datas, itemListiner)

    }

    class ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {

        fun bindView(data: CommentDto, listiner: OnListener) {
            itemView.tv_user_name.text = data.username
            itemView.tv_comment.text = data.comment


        }

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    dataFilterList = data as ArrayList<CommentDto>
                } else {
                    val resultList = ArrayList<CommentDto>()
                    for (row in data) {
                        if (row.username?.toString()?.toLowerCase(Locale.ROOT)?.contains(charSearch.toLowerCase(Locale.ROOT))!!) {
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
                dataFilterList = results?.values as ArrayList<CommentDto>
                notifyDataSetChanged()
            }

        }
    }


}