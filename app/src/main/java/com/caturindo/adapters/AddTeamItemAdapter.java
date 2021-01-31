package com.caturindo.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.caturindo.R;
import com.caturindo.models.UserDto;
import com.caturindo.models.UserModel;

import java.util.ArrayList;

public class AddTeamItemAdapter extends RecyclerView.Adapter<AddTeamItemAdapter.AddTeamViewHolder> {

    private Activity activity;
    private ArrayList<UserDto> teams;

    public AddTeamItemAdapter(Activity activity, ArrayList<UserDto> teams) {
        this.activity = activity;
        this.teams = teams;
    }

    @NonNull
    @Override
    public AddTeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_add_team, parent, false);
        return new AddTeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddTeamViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public class AddTeamViewHolder  extends RecyclerView.ViewHolder{


        public AddTeamViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
