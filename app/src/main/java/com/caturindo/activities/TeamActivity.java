package com.caturindo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.caturindo.R;
import com.caturindo.adapters.TeamItemAdapter;
import com.caturindo.models.UserModel;

import java.util.ArrayList;

public class TeamActivity extends AppCompatActivity {
    private ImageView menuMore;
    private ImageView menuList;
    private ImageView userAvatar;
    private RecyclerView rvTeams;
    private TeamItemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        bindView();
        setupButtonAction();
        setupUserInformation();
        setupUserTeams();
    }

    private void bindView(){
        userAvatar = findViewById(R.id.profile_image);
        menuList = findViewById(R.id.img_menu);
        menuMore = findViewById(R.id.img_more);
    }

    private void setupButtonAction(){
        menuList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        menuMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeamActivity.this,AddTeamActivity.class));
            }
        });
    }

    private void setupUserInformation(){
        Glide.with(this)
                .load(ContextCompat.getDrawable(this,R.drawable.caturindo_32))
                .apply(new RequestOptions().circleCrop())
                .into(userAvatar);
    }

    private void setupUserTeams() {
        //data dummy
        ArrayList<UserModel> teams = new ArrayList();
        teams.add(new UserModel());
        teams.add(new UserModel());
        teams.add(new UserModel());
        teams.add(new UserModel());
        teams.add(new UserModel());

        rvTeams = findViewById(R.id.rv_team);
        adapter = new TeamItemAdapter(this,teams);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration myDivider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        myDivider.setDrawable(ContextCompat.getDrawable(this, R.drawable.custom_divider));

        rvTeams.setLayoutManager(manager);
        rvTeams.addItemDecoration(myDivider);
        rvTeams.setAdapter(adapter);

    }


}