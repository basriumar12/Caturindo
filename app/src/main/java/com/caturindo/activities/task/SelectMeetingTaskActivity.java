package com.caturindo.activities.task;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.caturindo.R;
import com.caturindo.activities.meeting.list.ListMeetingActivity;
import com.caturindo.activities.meeting.list.ListSubMeetingActivity;

public class SelectMeetingTaskActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView mTitle;
    private ImageView mNavigationMenu;
    private RadioGroup radioGroup;
    private RadioButton indoorRadio;
    private RadioButton outdoorRadio;
    private Button btnBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_task_meeting);
        setupToolbar();
        setupNavigationMenu();
        setupRadioButton();
    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        mTitle = findViewById(R.id.tv_toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText("Meeting");
        setupNavigationMenu();
    }

    private void setupNavigationMenu() {
        ImageView img = findViewById(R.id.img_first_option);
        ImageView img2 = findViewById(R.id.img_second_option);
        img.setVisibility(View.GONE);
        img2.setVisibility(View.GONE);
        mNavigationMenu = findViewById(R.id.img_toolbar_start_button);
        mNavigationMenu.setImageResource(R.drawable.ic_baseline_chevron_left_24);
        mNavigationMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setupRadioButton() {
        radioGroup = (RadioGroup) findViewById(R.id.radio_wrapper);
        indoorRadio = (RadioButton) findViewById(R.id.radio_inside);
        outdoorRadio = (RadioButton) findViewById(R.id.radio_outside);
        btnBook = (Button) findViewById(R.id.btn_book);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio_inside || checkedId == R.id.radio_outside) {
                    //Your code
                    btnBook.setVisibility(View.VISIBLE);

                    if (checkedId == R.id.radio_inside){
                        btnBook.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(SelectMeetingTaskActivity.this, ListMeetingActivity.class));
                                finish();
                            }
                        });
                    }

                    if (checkedId == R.id.radio_outside){
                        btnBook.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(SelectMeetingTaskActivity.this, ListSubMeetingActivity.class));
                                finish();
                            }
                        });
                    }
                } else {
                    //Your code

                    btnBook.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void onRadioButtonClicked(View view) {

    }
}