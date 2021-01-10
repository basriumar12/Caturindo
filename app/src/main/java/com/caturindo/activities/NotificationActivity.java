package com.caturindo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.caturindo.R;
import com.caturindo.adapters.NotificationItemAdapter;
import com.caturindo.models.NotificationModel;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView mTitle;
    private ImageView mNavigationMenu;
    private RecyclerView rvNotification;
    private NotificationItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        setupToolbar();
        setupRecyclerView();
    }

    private void setupToolbar(){
        toolbar = findViewById(R.id.toolbar);
        mTitle  =findViewById(R.id.tv_toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText("Notification");
        setupNavigationMenu();
    }

    private void setupNavigationMenu(){
        mNavigationMenu = findViewById(R.id.img_toolbar_start_button);
        mNavigationMenu.setImageResource(R.drawable.ic_baseline_chevron_left_24);
        mNavigationMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setupRecyclerView(){
        ArrayList<NotificationModel> notificationModels = generateDummyData();
        adapter = new NotificationItemAdapter(this, notificationModels);
        rvNotification = findViewById(R.id.rv_notification);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvNotification.setLayoutManager(manager);
        DividerItemDecoration myDivider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        myDivider.setDrawable(ContextCompat.getDrawable(this, R.drawable.custom_divider));
        rvNotification.addItemDecoration(myDivider);
        rvNotification.setAdapter(adapter);
    }

    private ArrayList<NotificationModel> generateDummyData(){
        ArrayList<NotificationModel> notificationModels = new ArrayList<>();
        notificationModels.add(new NotificationModel("TASK-02","John Doe Tagged you on a task","lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua","task"));
        notificationModels.add(new NotificationModel("MEETING-02","John Doe Tagged you on a meeting","lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua","meeting"));
        notificationModels.add(new NotificationModel("TAG-02","John Doe Tagged you in a comment","lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua","tag"));
        notificationModels.add(new NotificationModel("REMINDER-02","John, you have 1 day to get task done","lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua","reminder"));
        return notificationModels;
    }
}