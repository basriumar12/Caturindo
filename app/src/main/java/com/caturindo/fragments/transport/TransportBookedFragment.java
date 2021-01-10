package com.caturindo.fragments.transport;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.caturindo.R;
import com.caturindo.activities.RoomDetailActivity;
import com.caturindo.activities.TransportDetailActivity;
import com.caturindo.adapters.TransportItemAdapter;
import com.caturindo.models.TransportItemModel;

import java.util.ArrayList;

public class TransportBookedFragment extends Fragment  implements TransportItemAdapter.ItemListener{

    private View rootView;
    private TransportItemAdapter adapter;
    private RecyclerView rvTransport;
    private ArrayList<TransportItemModel> itemlist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_transport_booked, null);
        rvTransport = rootView.findViewById(R.id.rvTransportBooked);
        itemlist = new ArrayList<TransportItemModel>();
        itemlist.add(new TransportItemModel(1, "Toyota Avanza", 0, 7, R.drawable.ic_launcher_foreground));
        itemlist.add(new TransportItemModel(2, "Daihatsu Xenia", 0, 7, R.drawable.ic_launcher_foreground));
        itemlist.add(new TransportItemModel(3, "Toyota Rush", 0, 7, R.drawable.ic_launcher_foreground));
        itemlist.add(new TransportItemModel(4, "Daihatsu Terios", 0, 7, R.drawable.ic_launcher_foreground));
        adapter = new TransportItemAdapter(getContext(), itemlist, this);
        rvTransport.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        rvTransport.setLayoutManager(manager);

        return rootView;
    }

    @Override
    public void onItemClick(TransportItemModel item) {
        startActivity(new Intent(getActivity(), TransportDetailActivity.class));
    }
}
