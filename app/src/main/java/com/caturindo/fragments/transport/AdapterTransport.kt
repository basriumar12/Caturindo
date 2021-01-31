package com.caturindo.fragments.transport

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.caturindo.R
import com.caturindo.models.TransportDto
import kotlinx.android.synthetic.main.item_room.view.*
import kotlinx.android.synthetic.main.item_transport.view.*

class AdapterTransport(val context: Context, val data: MutableList<TransportDto>,
                       private val itemListiner: OnListener
) :
        RecyclerView.Adapter<AdapterTransport.ViewHolder>() {
    companion object {
        const val ON_CLICK_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_transport, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    interface OnListener {
        fun onClick(data: TransportDto)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        data?.get(position).let { data ->
            holder.bindView(data, itemListiner)
            holder.itemView.tvTransportItemNameCar.text = data.nameTransport + " / " + data.id
            holder.itemView.tvTransportItemNameCapacity.text = data.maxPeople
            if (data.image.isNullOrEmpty()) {
                Glide.with(holder.itemView.context)
                        .load("https://static.thenounproject.com/png/1554489-200.png")
                        .error(R.drawable.no_imge)
                        .into(holder.itemView.ivTransportItemImage)
            } else {
                Glide.with(holder.itemView.context)
                        .load(data.image?.get(0).toString())
                        .error(R.drawable.no_imge)
                        .into(holder.itemView.ivTransportItemImage)
            }

        }
    }

    class ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {

        fun bindView(data: TransportDto, listiner: OnListener) {
            itemView.setOnClickListener {
                listiner.onClick(data)
            }


        }

    }


}