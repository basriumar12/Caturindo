package com.caturindo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.caturindo.R;
import com.caturindo.adapters.AddTeamItemAdapter;
import com.caturindo.models.UserModel;

import java.util.ArrayList;

public class AddTeamActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView mTitle;
    private ImageView mNavigationMenu;
    private RecyclerView rvMember;
    private AddTeamItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_team);
        setupToolbar();
        setupMemberList();
    }

    private void setupToolbar(){
        toolbar = findViewById(R.id.toolbar);
        mTitle  =findViewById(R.id.tv_toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText("Add Team");
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

    private void setupMemberList(){
        ArrayList<UserModel> members = new ArrayList();
        members.add(new UserModel());
        members.add(new UserModel());
        members.add(new UserModel());
        members.add(new UserModel());
        rvMember = findViewById(R.id.rv_member);
        adapter = new AddTeamItemAdapter(this,members);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvMember.setLayoutManager(manager);
        rvMember.setAdapter(adapter);
    }


}