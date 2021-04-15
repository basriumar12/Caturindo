package com.caturindo.activities.meeting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.caturindo.R;
import com.caturindo.fragments.meeting.MeetingFragment;
import com.caturindo.fragments.room.RoomFragment;
import com.caturindo.fragments.task.TaskFragment;
import com.caturindo.fragments.transport.TransportFragment;
import com.caturindo.preference.Prefuser;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MeetingActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private MeetingActivity self = this;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);


        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        String saveMenu = new Prefuser().getSaveSessionMenu();

        if (saveMenu.equals("Y")){
            loadFragment(new MeetingFragment());
        }else {
            loadFragment(new TaskFragment());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    public boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.nav_task:
                fragment = new TaskFragment();
                new Prefuser().setSaveSessionMenu("N");
                break;
            case R.id.nav_meeting:
                fragment = new MeetingFragment();
                break;
            case R.id.nav_room:

               new Prefuser().setvalidateOnclickRoom("0");
                fragment = new RoomFragment();
                new Prefuser().setSaveSessionMenu("N");
                break;
            case R.id.nav_transport:

                new Prefuser().setvalidateOnclickRoom("0");
                fragment = new TransportFragment();
                new Prefuser().setSaveSessionMenu("N");
                break;
        }

        return loadFragment(fragment);
    }
}