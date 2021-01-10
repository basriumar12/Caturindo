package com.caturindo.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MeetingViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public MeetingViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        /*
        Fragment fragment = null;

        if (position == 0){
            fragment = new MeetingCurrentFragment();
        }
        else if (position == 1){
            fragment = new MeetingPastFragment();
        }
        return fragment;
        */
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        //return 2;
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        /*
        String title = null;
        if (position == 0){
            title = "Current";
        }
        else if (position == 1){
            title = "Past";
        }

        return title;
        */
        return mFragmentTitleList.get(position);
    }
}