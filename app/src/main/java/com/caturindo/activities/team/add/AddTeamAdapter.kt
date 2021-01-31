package com.caturindo.activities.team.add

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caturindo.R
import com.caturindo.models.UserDto
import java.util.*

class AddTeamAdapter(private val activity: Activity, private val teams: ArrayList<UserDto>)
    : RecyclerView.Adapter<AddTeamAdapter.AddTeamViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddTeamViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.item_add_team, parent, false)
        return AddTeamViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddTeamViewHolder, position: Int) {}
    override fun getItemCount(): Int {
        return teams.size
    }

    inner class AddTeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}