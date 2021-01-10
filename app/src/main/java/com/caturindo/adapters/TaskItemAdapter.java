package com.caturindo.adapters;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.caturindo.R;
import com.caturindo.models.TaskModel;

import java.util.ArrayList;

public class TaskItemAdapter extends RecyclerView.Adapter<TaskItemAdapter.MeetingViewHolder> {
    private ArrayList<TaskModel> mValues;
    private Context mContext;
    private ItemListener mListener;

    public TaskItemAdapter(Context context, ArrayList values, ItemListener itemListener) {
        mValues = values;
        mContext = context;
        mListener = itemListener;
    }

    public class MeetingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title, description, date,participantCount;
        public LinearLayout cv;
        TaskModel item;

        public MeetingViewHolder(View v) {
            super(v);

            v.setOnClickListener(this);
            title = v.findViewById(R.id.tv_task_name);
            description = v.findViewById(R.id.tv_task_desc);
            date = v.findViewById(R.id.tv_task_date);
            participantCount = v.findViewById(R.id.tv_task_participant_count);
            cv = v.findViewById(R.id.cv_task);
        }

        public void setData(TaskModel item) {
            this.item = item;
            title.setText(item.getTitle());
            description.setText(item.getDescription());
            date.setText(item.getDate());
            participantCount.setText(""+item.getParticipants().size());
            Log.d("teskitemadapter","type : "+item.getType());
            if(item.getType().equals("inside")){
                Log.d("teskitemadapter","type inside: "+item.getType());
                cv.setBackground(ContextCompat.getDrawable(itemView.getContext(),R.drawable.task_gradient_inside));
            }else{
                Log.d("teskitemadapter","type outside: "+item.getType());
                cv.setBackground(ContextCompat.getDrawable(itemView.getContext(),R.drawable.task_gradient_outside));
            }
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }

    @Override
    public MeetingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_task, parent, false);
        return new MeetingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MeetingViewHolder holder, int position) {
        holder.setData(mValues.get(position));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public interface ItemListener {
        void onItemClick(TaskModel item);
    }
}
