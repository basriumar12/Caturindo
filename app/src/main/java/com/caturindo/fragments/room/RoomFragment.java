package com.caturindo.fragments.room;

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
import com.caturindo.adapters.RoomViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class RoomFragment extends Fragment {

    private View rootView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private RoomViewPagerAdapter viewPagerAdapter;
    private Toolbar toolbar;
    private TextView mTitle;
    private ImageView mMainMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_room, null);
        tabLayout = rootView.findViewById(R.id.tabLayoutRoom);
        viewPager = rootView.findViewById(R.id.viewPagerRoom);

        viewPagerAdapter = new RoomViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new RoomAvailableFragment(), "Available");
        viewPagerAdapter.addFragment(new RoomBookedFragment(), "Booked");
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setSaveFromParentEnabled(false);
        tabLayout.setupWithViewPager(viewPager);

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
        mTitle.setText("Room");
        setupNavigationMenu();
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

}
