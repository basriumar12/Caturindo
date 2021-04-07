package com.caturindo.activities.team

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.caturindo.R
import com.caturindo.activities.grup.model.ResponseGroupDto
import com.caturindo.preference.Prefuser
import kotlinx.android.synthetic.main.item_grup_list.view.*
import java.util.*
import kotlin.collections.ArrayList

class AdapterGroup(val context: Context, val data: MutableList<ResponseGroupDto>,
                   private val itemListiner: OnListener
) :
        RecyclerView.Adapter<AdapterGroup.ViewHolder>(), Filterable {

    var dataFilterList = ArrayList<ResponseGroupDto>()

    init {
        dataFilterList = data as ArrayList<ResponseGroupDto>
    }

    companion object {
        const val ON_CLICK_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_grup_list, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return dataFilterList.size
    }

    interface OnListener {
        fun onClickGrup(data: ResponseGroupDto)
        fun onDeleteGrup(data: ResponseGroupDto)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val datas = dataFilterList[position]
        holder.bindView(datas, itemListiner)

    }

    class ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {

        fun bindView(data: ResponseGroupDto, listiner: OnListener) {
           
            itemView.setOnClickListener {
                listiner.onClickGrup(data)
            }

            itemView.img_delete.setOnClickListener {
                listiner.onDeleteGrup(data)
            }

            if (Prefuser().getUser()?.role.equals("3")){
                itemView.img_delete.visibility = View.GONE
            }
            itemView.tv_grup_name.text = data.namaTeam
            itemView.tv_member.text = data.total_anggota +" Member"


            
        }
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    dataFilterList = data as ArrayList<ResponseGroupDto>
                } else {
                    val resultList = ArrayList<ResponseGroupDto>()
                    for (row in data) {
                        if (row.namaTeam?.toString()?.toLowerCase(Locale.ROOT)?.contains(charSearch.toLowerCase(Locale.ROOT))!!) {
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
                dataFilterList = results?.values as ArrayList<ResponseGroupDto>
                notifyDataSetChanged()
            }

        }
    }


}