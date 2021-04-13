package com.caturindo.activities.meeting.detail

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caturindo.R
import com.caturindo.activities.task.detail.ImageActivity
import kotlinx.android.synthetic.main.view_item_image_upload.view.*

class AdapterListFileUpload (private val item: ArrayList<String>, private val contex: Context)
    : RecyclerView.Adapter<AdapterListFileUpload.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder = ViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.view_item_image_upload, parent, false))

    override fun getItemCount(): Int {
        var size = item.size
        if (size == null || size== 0){
            size = 0
        } else{
            size
        }
        return size
    }

    fun removeAt(position: Int) {
        item.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val adaterPosition = 1+position
        holder.itemView.img_close.visibility =View.GONE
        holder.itemView.tv_file_name.text ="File  ${adaterPosition}"
        holder.itemView.img_close.setOnClickListener {
            removeAt(position)
        }

        holder.itemView.setOnClickListener {
            val originalString = item.toString()

            val newString: String = originalString.substring(originalString.length - 4)
            Log.e("TAG","remove string $newString")
            if (originalString.contains("pdf")){
                    contex.startActivity(Intent(holder.itemView.context, PdfActivity::class.java)
                            .putExtra("IMAGE",item.get(position).toString())
                    )
            }else{
                contex.startActivity(Intent(holder.itemView.context, ImageActivity::class.java)
                        .putExtra("IMAGE",item.get(position)))
            }
        }





    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(cart: String) {

            with(cart) {



            }
        }

    }
}