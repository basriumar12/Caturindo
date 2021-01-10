package com.caturindo.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.caturindo.R;
import com.caturindo.models.UserModel;

import java.util.ArrayList;

public class TeamItemAdapter extends RecyclerView.Adapter<TeamItemAdapter.TeamViewHolder> {

    private Activity activity;
    private ArrayList<UserModel> teams;

    public TeamItemAdapter(Activity activity, ArrayList<UserModel> teams) {
        this.activity = activity;
        this.teams = teams;
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_team_list, parent, false);
        return new TeamItemAdapter.TeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        //TODO load teams information
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public class TeamViewHolder extends RecyclerView.ViewHolder {
        public TeamViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
