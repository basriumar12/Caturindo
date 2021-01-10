package com.caturindo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.caturindo.R;

public class CreateMeetingActivity extends AppCompatActivity {

    private CreateMeetingActivity self = this;
    private Toolbar toolbar;
    private TextView mTitle;
    private ImageView mNavigationMenu;
    private TextView tvLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);
        setupToolbar();
        setupLocationAction();
    }

    private void setupToolbar(){
        toolbar = findViewById(R.id.toolbar);
        mTitle  =findViewById(R.id.tv_toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText("Create Meeting");
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

    private void setupLocationAction(){
        tvLocation = findViewById(R.id.et_location);
        tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(CreateMeetingActivity.this,SelectLocationActivity.class),24);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                self.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}