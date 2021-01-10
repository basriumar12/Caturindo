package com.caturindo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.caturindo.R;
import com.caturindo.models.MeetingModel;

import java.util.ArrayList;

public class MeetingItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private static int TYPE_INDOOR = 1;
    private static int TYPE_OUTDOOR = 2;

    private Context context;
    private ArrayList<MeetingModel> meetingModels;


    public MeetingItemAdapter(Context context, ArrayList<MeetingModel> meetingModels) {
        this.context = context;
        this.meetingModels = meetingModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_INDOOR) { // for call layout
            view = LayoutInflater.from(context).inflate(R.layout.item_meeting_indoor, parent, false);
            return new MeetingItemAdapter.IndoorMeeting(view);
        } else { // for email layout
            view = LayoutInflater.from(context).inflate(R.layout.item_meeting_outdoor, parent, false);
            return new MeetingItemAdapter.OutdoorMeeting(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_INDOOR) {
            ((MeetingItemAdapter.IndoorMeeting) holder).setMeetingDetail(meetingModels.get(position));
        } else {
            ((MeetingItemAdapter.OutdoorMeeting) holder).setMeetingDetail(meetingModels.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return meetingModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (meetingModels.get(position).getType() == 1) {
            return TYPE_INDOOR;
        }
        return TYPE_OUTDOOR;
    }


    class IndoorMeeting extends RecyclerView.ViewHolder {

        private TextView txtTitle;
        private TextView txtDesc;
        private TextView txtDate;
        private TextView txtTime;
        private TextView txtParticipant;
        private TextView txtLocation;

        IndoorMeeting(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.tv_meeting_title);
            txtDesc = itemView.findViewById(R.id.tv_meeting_desc);
            txtDate = itemView.findViewById(R.id.tv_meeting_date);
            txtTime = itemView.findViewById(R.id.tv_meeting_time);
            txtParticipant = itemView.findViewById(R.id.tv_meeting_participant);
            txtLocation = itemView.findViewById(R.id.tv_meeting_loc);

        }

        void setMeetingDetail(MeetingModel meetingModel) {
            txtTitle.setText(meetingModel.getTitle());
            txtDesc.setText(meetingModel.getDesc());
            txtDate.setText(meetingModel.getDate());
            txtTime.setText(meetingModel.getTime());
            txtParticipant.setText(""+meetingModel.getParticipant());
            txtLocation.setText(meetingModel.getLocation());
        }
    }

    class OutdoorMeeting extends RecyclerView.ViewHolder {

        private TextView txtTitle;
        private TextView txtDesc;
        private TextView txtDate;
        private TextView txtTime;
        private TextView txtParticipant;
        private TextView txtLocation;


        OutdoorMeeting(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.tv_meeting_title);
            txtDesc = itemView.findViewById(R.id.tv_meeting_desc);
            txtDate = itemView.findViewById(R.id.tv_meeting_date);
            txtTime = itemView.findViewById(R.id.tv_meeting_time);
            txtParticipant = itemView.findViewById(R.id.tv_meeting_participant);
            txtLocation = itemView.findViewById(R.id.tv_meeting_loc);
        }

        void setMeetingDetail(MeetingModel meetingModel) {
            txtTitle.setText(meetingModel.getTitle());
            txtDesc.setText(meetingModel.getDesc());
            txtDate.setText(meetingModel.getDate());
            txtTime.setText(meetingModel.getTime());
            txtParticipant.setText(""+meetingModel.getParticipant());
            txtLocation.setText(meetingModel.getLocation());
        }
    }
}
