package com.caturindo.activities.team

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
import com.caturindo.activities.team.model.MemberItem
import com.caturindo.models.UserDto
import kotlinx.android.synthetic.main.item_add_team.view.*
import kotlinx.android.synthetic.main.item_team_list.view.*
import java.util.*
import kotlin.collections.ArrayList

class AdapterTeam(val context: Context, val data: MutableList<MemberItem>,
                  private val itemListiner: OnListener
) :
        RecyclerView.Adapter<AdapterTeam.ViewHolder>(), Filterable {

    var dataFilterList = ArrayList<MemberItem>()

    init {
        dataFilterList = data as ArrayList<MemberItem>
    }

    companion object {
        const val ON_CLICK_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_team_list, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return dataFilterList.size
    }

    interface OnListener {
        fun onClick(data: MemberItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val datas = dataFilterList[position]
        holder.bindView(datas, itemListiner)

    }

    class ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {

        fun bindView(data: MemberItem, listiner: OnListener) {
            itemView.telvon.setOnClickListener {
                listiner.onClick(data)
            }

            var name = ""
            var phone = data.phone?.replaceFirst("+62", "")



            itemView.tv_team_name.text = data.username
            itemView.tv_team_level.text = data.jabatan
            itemView.tv_team_email.text = data.email

            itemView.wa.setOnClickListener {
                try {
                    itemView.context.startActivity(
                            Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(
                                            String.format(
                                                    "https://api.whatsapp.com/send?phone=%s&text=%s",
                                                    "+62${phone}",
                                                    "Hi "
                                            ))))
                } catch (e: NullPointerException) {

                } catch (e: Exception) {

                }
            }
        }
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    dataFilterList = data as ArrayList<MemberItem>
                } else {
                    val resultList = ArrayList<MemberItem>()
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
                dataFilterList = results?.values as ArrayList<MemberItem>
                notifyDataSetChanged()
            }

        }
    }


}