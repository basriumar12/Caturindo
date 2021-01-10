package com.caturindo.fragments.meeting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.caturindo.R;
import com.caturindo.adapters.MeetingItemAdapter;
import com.caturindo.models.MeetingModel;

import java.util.ArrayList;

public class MeetingPastFragment extends Fragment {

    private View rootView;
    private RecyclerView rvMeeting;
    private MeetingItemAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_meeting_past, null);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupMeetingList();
    }

    private void setupMeetingList(){
        rvMeeting = rootView.findViewById(R.id.rv_meeting);
        adapter = new MeetingItemAdapter(getContext(),generateDummy());
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration myDivider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        myDivider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.custom_divider));

        rvMeeting.setLayoutManager(manager);
        rvMeeting.addItemDecoration(myDivider);
        rvMeeting.setAdapter(adapter);

    }

    private ArrayList<MeetingModel> generateDummy(){
        ArrayList<MeetingModel> models = new ArrayList<MeetingModel>();
        models.add(new MeetingModel(
                "1",
                "Meeting 1 past","orem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore",
                "26/02/2020",
                "09:23",
                5,
                "Kantor A",
                1));
        models.add(new MeetingModel(
                "2",
                "Meeting 2 past","orem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore",
                "26/02/2020",
                "09:23",
                10,
                "Kantor B",
                2));
        models.add(new MeetingModel(
                "3",
                "Meeting 3 past","orem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore",
                "26/02/2020",
                "09:23",
                10,
                "Kantor AA",
                2));

        return models;
    }

}
