package com.caturindo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.caturindo.R;
import com.caturindo.models.NotificationModel;

import java.util.ArrayList;

public class NotificationItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static int TYPE_TASK = 1;
    private static int TYPE_MEETING = 2;
    private static int TYPE_TAG = 3;
    private static int TYPE_REMINDER = 4;

    private Context context;
    private ArrayList<NotificationModel> notificationModels;

    public NotificationItemAdapter(Context context, ArrayList<NotificationModel> notificationModels) {
        this.context = context;
        this.notificationModels = notificationModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_TASK) { // for call layout
            view = LayoutInflater.from(context).inflate(R.layout.item_notification_task, parent, false);
            return new TaskViewHolder(view);
        } else if (viewType == TYPE_MEETING) { // for email layout
            view = LayoutInflater.from(context).inflate(R.layout.item_notification_meeting, parent, false);
            return new MeetingViewHolder(view);
        } else if (viewType == TYPE_TAG) {
            view = LayoutInflater.from(context).inflate(R.layout.item_notification_tag, parent, false);
            return new TagViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_notification_reminder, parent, false);
            return new ReminderViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_TASK) {
            ((TaskViewHolder) holder).setTaskDetail(notificationModels.get(position));
        } else if (getItemViewType(position) == TYPE_MEETING) {
            ((MeetingViewHolder) holder).setMeetingDetail(notificationModels.get(position));
        } else if (getItemViewType(position) == TYPE_TAG) {
            ((TagViewHolder) holder).setTagDetail(notificationModels.get(position));
        } else {
            ((ReminderViewHolder) holder).setReminderDetail(notificationModels.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return notificationModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (notificationModels.get(position).getType()) {
            case "meeting":
                return TYPE_MEETING;
            case "task":
                return TYPE_TASK;
            case "tag":
                return TYPE_TAG;
            default:
                return TYPE_REMINDER;
        }
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTitle;
        private TextView txtTaskId;
        private TextView txtContent;

        TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.tv_notification_title);
            txtTaskId = itemView.findViewById(R.id.tv_task_title);
            txtContent = itemView.findViewById(R.id.tv_task_content);
        }

        void setTaskDetail(NotificationModel notificationModel) {
            txtTitle.setText(notificationModel.getTitle());
            txtTaskId.setText(notificationModel.getId());
            txtContent.setText(notificationModel.getDescription());
        }
    }

    class MeetingViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTitle;
        private TextView txtTaskId;
        private TextView txtContent;

        MeetingViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.tv_notification_title);
            txtTaskId = itemView.findViewById(R.id.tv_task_title);
            txtContent = itemView.findViewById(R.id.tv_task_content);
        }

        void setMeetingDetail(NotificationModel notificationModel) {
            txtTitle.setText(notificationModel.getTitle());
            txtTaskId.setText(notificationModel.getId());
            txtContent.setText(notificationModel.getDescription());
        }
    }

    class TagViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTitle;
        private TextView txtTaskId;
        private TextView txtContent;
        private ImageView imgPerson;

        TagViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.tv_notification_title);
            txtTaskId = itemView.findViewById(R.id.tv_task_title);
            txtContent = itemView.findViewById(R.id.tv_task_content);
            imgPerson = itemView.findViewById(R.id.img_person);
        }

        void setTagDetail(NotificationModel notificationModel) {
            txtTitle.setText(notificationModel.getTitle());
            txtTaskId.setText(notificationModel.getId());
            txtContent.setText(notificationModel.getDescription());
            //todo handle image
        }
    }

    class ReminderViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTitle;
        private TextView txtTaskId;
        private TextView txtContent;

        ReminderViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.tv_notification_title);
            txtTaskId = itemView.findViewById(R.id.tv_task_title);
            txtContent = itemView.findViewById(R.id.tv_task_content);
        }

        void setReminderDetail(NotificationModel notificationModel) {
            txtTitle.setText(notificationModel.getTitle());
            txtTaskId.setText(notificationModel.getId());
            txtContent.setText(notificationModel.getDescription());
        }
    }
}
