package com.caturindo.fragments.transport;

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
import com.caturindo.activities.notif.NotificationActivity;
import com.caturindo.adapters.TransportViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class TransportFragment extends Fragment{

    private View rootView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TransportViewPagerAdapter viewPagerAdapter;
    private Toolbar toolbar;
    private TextView mTitle;
    private ImageView mMainMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_transport, null);
        tabLayout = rootView.findViewById(R.id.tabLayoutTransport);
        viewPager = rootView.findViewById(R.id.viewPagerTransport);

        viewPagerAdapter = new TransportViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new TransportAvailableFragment(), "Available");
        viewPagerAdapter.addFragment(new TransportBookedFragment(), "Booked");
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
        mTitle.setText("Transport");
        setupNavigationMenu();
        ImageView img2 = rootView.findViewById(R.id.img_second_option);
        img2.setVisibility(View.GONE);
        ImageView img = rootView.findViewById(R.id.img_first_option);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(rootView.getContext(), NotificationActivity.class));
            }
        });
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
