package com.caturindo.fragments.room

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.caturindo.R
import com.caturindo.models.RoomDto
import kotlinx.android.synthetic.main.item_room.view.*

class AdapterRoom(val context: Context, val data: MutableList<RoomDto>,
                  private val itemListiner: OnListener
                    ) :
    RecyclerView.Adapter<AdapterRoom.ViewHolder>() {
    companion object {
        const val ON_CLICK_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_room, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }
    interface OnListener {
        fun onClick(data: RoomDto)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        data?.get(position).let { data ->
            holder.itemView.tvRoomItemNameRoom.text = data.nameRoom
            if (data.statusBooking.equals("1") ) {
                holder.itemView.tvRoomItemNameAvailable.setText("Available")
                holder.itemView.ivRoomItemImageAvailable.setImageResource(R.drawable.ic_icon_unchecked)
            } else {
                holder.itemView.tvRoomItemNameAvailable.setText("Booked")
                holder.itemView.ivRoomItemImageAvailable.setImageResource(R.drawable.ic_icon_checked)
            }
            holder.itemView.tvRoomItemNameCapacity.setText(data.maxPeople.toString())
            Glide.with(holder.itemView.context)
                    .load(data.image?.get(0).toString()
                    )

        }
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bindView(data: RoomDto, listiner: OnListener){
            itemView.setOnClickListener {
                listiner.onClick(data)
            }


        }

    }


}