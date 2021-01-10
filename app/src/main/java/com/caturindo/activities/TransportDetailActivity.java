package com.caturindo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.caturindo.R;
import com.caturindo.adapters.TransportDetailItemAdapter;

import java.util.ArrayList;

public class TransportDetailActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView mTitle;
    private ImageView mNavigationMenu;
    private RecyclerView rvTransport;
    private LinearLayout bookingForm;
    private SwitchCompat tglBtn;
    private TransportDetailItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_detail);
        setupToolbar();
        setupRecyclerView();
        setupToggleButton();
    }

    private void setupToggleButton() {
        tglBtn = findViewById(R.id.switch_btn_booking);
        bookingForm = findViewById(R.id.booking_form_wrapper);
        tglBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    bookingForm.setVisibility(View.VISIBLE);
                }else{
                    bookingForm.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setupToolbar(){
        toolbar = findViewById(R.id.toolbar);
        mTitle  =findViewById(R.id.tv_toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText("Nama/Car Code");
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

    private void setupRecyclerView(){
        ArrayList<String> images = new ArrayList();
        images.add("test1");
        images.add("test2");
        images.add("test3");
        images.add("test4");
        images.add("test4");
        images.add("test4");
        images.add("test4");
        images.add("test4");
        adapter = new TransportDetailItemAdapter(images,this);
        rvTransport =findViewById(R.id.rv_room_image);
        GridLayoutManager manager = new GridLayoutManager(this, 4, RecyclerView.VERTICAL, false);
        rvTransport.setLayoutManager(manager);
        rvTransport.setAdapter(adapter);
    }

}