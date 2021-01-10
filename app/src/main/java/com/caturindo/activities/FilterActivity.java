package com.caturindo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.caturindo.R;
import com.caturindo.adapters.FilterItemAdapter;
import com.caturindo.adapters.TransportDetailItemAdapter;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView mTitle;
    private ImageView mNavigationMenu;
    private ExpandableListView rvFilter;
    private FilterItemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        bindView();
        setupToolbar();
        setupRecyclerView();
    }

    private void bindView(){
        toolbar = findViewById(R.id.toolbar);
        mTitle  =findViewById(R.id.tv_toolbar_title);
        mNavigationMenu = findViewById(R.id.img_toolbar_start_button);
        rvFilter =findViewById(R.id.rv_filter);
    }

    private void setupToolbar(){
        setSupportActionBar(toolbar);
        mTitle.setText("Filter");
        setupNavigationMenu();
    }

    private void setupNavigationMenu(){

        mNavigationMenu.setImageResource(R.drawable.ic_baseline_chevron_left_24);
        mNavigationMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setupRecyclerView(){
        adjustArrowList();
        ArrayList<String> images = new ArrayList();
        images.add("2019");
        images.add("2018");
        images.add("2017");
        images.add("2016");
        adapter = new FilterItemAdapter(this,images);
        rvFilter.setAdapter(adapter);
    }

    private void adjustArrowList(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                50, r.getDisplayMetrics());
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            rvFilter.setIndicatorBounds(width - px, width);
        } else {
            rvFilter.setIndicatorBoundsRelative(width - px, width);
        }
    }


}