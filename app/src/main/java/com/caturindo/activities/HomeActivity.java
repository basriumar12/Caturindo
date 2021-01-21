package com.caturindo.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;

import com.caturindo.R;
import com.caturindo.activities.login.LoginActivity;
import com.caturindo.activities.meeting.MeetingActivity;
import com.caturindo.adapters.HomeItemAdapter;
import com.caturindo.models.HomeItemModel;
import com.caturindo.preference.Prefuser;
import com.caturindo.utils.AppConstant;
import com.google.gson.Gson;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements HomeItemAdapter.ItemListener{

    private HomeActivity self = this;
    private HomeItemAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<HomeItemModel> arraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.caturindo_32);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark)));
        getSupportActionBar().setTitle(" PT. Caturindo");

        recyclerView = (RecyclerView) findViewById(R.id.rvHome);
        arraylist = new ArrayList<HomeItemModel>();
        arraylist.add(new HomeItemModel(1, "Meeting", R.drawable.ic_menu_meeting));
        arraylist.add(new HomeItemModel(2, "Team", R.drawable.ic_menu_team));
        arraylist.add(new HomeItemModel(4, "KPI", R.drawable.ic_icon_kpi));
        arraylist.add(new HomeItemModel(5, "Productivity Achievement", R.drawable.ic_icon_productivity));
        arraylist.add(new HomeItemModel(6, "Code of Conduct", R.drawable.ic_icon_code_of_conduct));
        arraylist.add(new HomeItemModel(7, "Job", R.drawable.ic_icon_job));
        arraylist.add(new HomeItemModel(8, "MTN/OEE", R.drawable.ic_icon_mtn_oee));
        arraylist.add(new HomeItemModel(9, "Performance for Customer", R.drawable.ic_icon_performance_of_customer));
        arraylist.add(new HomeItemModel(10, "PPIC", R.drawable.ic_icon_ppic));
        arraylist.add(new HomeItemModel(11, "New Project", R.drawable.ic_icon_new_project));
        adapter = new HomeItemAdapter(this, arraylist, this);
        recyclerView.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        Log.e("TAG","cek user pref"+new Gson().toJson(new Prefuser().getUser()));

    }

    @Override
    public void onItemClick(HomeItemModel item) {
        switch (item.getId()){
            case 1 :
                Intent intent = new Intent(self, MeetingActivity.class);
                startActivity(intent);
                break;
            case 2:
                Intent intentTeam = new Intent(self, TeamActivity.class);
                startActivity(intentTeam);
                break;
            case 3 :
                AlertDialog.Builder builder = new AlertDialog.Builder(self);
                builder.setTitle("Logout Confirmation");
                builder.setMessage("Are you sure want to logout?");
                builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // clear data
                        new Prefuser().setUser(null);
                        SharedPreferences sessions = self.getSharedPreferences(AppConstant.SHARED_PREFERENCE_NAME, 0);
                        SharedPreferences.Editor edit = sessions.edit();
                        edit.clear();
                        edit.commit();
                        Intent intent = new Intent(self, LoginActivity.class);
                        startActivity(intent);
                        self.finish();
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();
                break;
        }
    }
}
