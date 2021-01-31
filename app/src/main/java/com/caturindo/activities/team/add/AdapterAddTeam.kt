package com.caturindo.activities.team.add

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.caturindo.R
import com.caturindo.models.UserDto
import kotlinx.android.synthetic.main.item_add_team.view.*
import java.util.*
import kotlin.collections.ArrayList

class AdapterAddTeam(val context: Context, val data: MutableList<UserDto>,
                     private val itemListiner: OnListener
) :
        RecyclerView.Adapter<AdapterAddTeam.ViewHolder>(), Filterable {

    var dataFilterList = ArrayList<UserDto>()

    init {
        dataFilterList = data as ArrayList<UserDto>
    }

    companion object {
        const val ON_CLICK_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_add_team, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return dataFilterList.size
    }

    interface OnListener {
        fun onClick(data: UserDto)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val datas = dataFilterList[position]
        holder.bindView(datas, itemListiner)

    }

    class ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {

        fun bindView(data: UserDto, listiner: OnListener) {
            itemView.btn_add.setOnClickListener {
                listiner.onClick(data)
            }
            itemView.tv_user_name.text = data.username
            var role = ""
            if (data.role?.toString().equals("2")) {
                role = "Manager"
            } else if (data.role.equals("3")) {
                role = "Staf"
            } else {
                role = ""
            }
            itemView.tv_user_priviledge.text = role.toString()

            if (data.username.equals("admin")) {
                itemView.layoutParams = RecyclerView.LayoutParams(0, 0)

            }


        }

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    dataFilterList = data as ArrayList<UserDto>
                } else {
                    val resultList = ArrayList<UserDto>()
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
                dataFilterList = results?.values as ArrayList<UserDto>
                notifyDataSetChanged()
            }

        }
    }


}