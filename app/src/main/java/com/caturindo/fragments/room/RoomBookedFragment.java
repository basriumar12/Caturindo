package com.caturindo.fragments.room;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.caturindo.R;
import com.caturindo.activities.RoomDetailActivity;
import com.caturindo.adapters.RoomItemAdapter;
import com.caturindo.adapters.TransportItemAdapter;
import com.caturindo.models.RoomDto;
import com.caturindo.models.RoomItemModel;
import com.caturindo.models.TransportItemModel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RoomBookedFragment extends Fragment implements AdapterRoom.OnListener,
        RoomContract.View
{
    private RoomPresenter presenter;
    private View rootView;
    private RoomItemAdapter adapter;
    private RecyclerView rvRoom;
    private ProgressBar progress_circular;
    private ArrayList<RoomItemModel> itemlist;
    LinearLayout linearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_room_booked, null);
        rvRoom = rootView.findViewById(R.id.rvRoomBooked);
        linearLayout = rootView.findViewById(R.id.paren_data_empty);
        progress_circular = rootView.findViewById(R.id.progress_circular);


        return rootView;
    }


    @Override
    public void onActivityCreated(@androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new RoomPresenter(this);
        presenter.getRoom("1");
    }

    @Override
    public void onClick(@NotNull RoomDto data) {
        startActivity(new Intent(getActivity(), RoomDetailActivity.class));
    }

    @Override
    public void showProgress() {
        progress_circular.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {

        progress_circular.setVisibility(View.GONE);
    }

    @Override
    public void onSuccessGet(List<RoomDto> data) {
        rvRoom.setLayoutManager(new LinearLayoutManager(getActivity()));
        AdapterRoom adapterRoom = new AdapterRoom(getActivity(),data,this);
        rvRoom.setAdapter(adapterRoom);
        rvRoom.setHasFixedSize(true);
        adapterRoom.notifyDataSetChanged();
        if (data.size() == 0 || data== null){
            linearLayout.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onErrorGetData(@Nullable String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
