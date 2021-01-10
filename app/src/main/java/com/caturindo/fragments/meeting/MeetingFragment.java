package com.caturindo.fragments.meeting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.caturindo.R;
import com.caturindo.activities.CreateMeetingActivity;
import com.caturindo.adapters.MeetingViewPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MeetingFragment extends Fragment {

    private View rootView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MeetingViewPagerAdapter viewPagerAdapter;
    private FloatingActionButton fabCreateMeeting;
    private Toolbar toolbar;
    private TextView mTitle;
    private ImageView mFilterOption;
    private ImageView mMainMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_meeting, null);
        tabLayout = rootView.findViewById(R.id.tabLayoutMeeting);
        viewPager = rootView.findViewById(R.id.viewPagerMeeting);

        viewPagerAdapter = new MeetingViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new MeetingCurrentFragment(), "Current");
        viewPagerAdapter.addFragment(new MeetingPastFragment(), "Past");
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setSaveFromParentEnabled(false);
        tabLayout.setupWithViewPager(viewPager);

        fabCreateMeeting = rootView.findViewById(R.id.fabCreateMeeting);
        fabCreateMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CreateMeetingActivity.class);
                startActivity(intent);
                //Toast.makeText(getContext(), "Create Meeting", Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupToolbar();
    }

    private void setupToolbar() {
        toolbar = rootView.findViewById(R.id.toolbar);
        mTitle = rootView.findViewById(R.id.tv_toolbar_title);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        mTitle.setText("Meeting");
        setupNavigationMenu();
        setupOptionsMenu();
    }

    private void setupNavigationMenu() {
        mMainMenu = rootView.findViewById(R.id.img_toolbar_start_button);
        mMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    private void setupOptionsMenu() {
        mFilterOption = rootView.findViewById(R.id.img_second_option);
        mFilterOption.setVisibility(View.VISIBLE);

        mFilterOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}