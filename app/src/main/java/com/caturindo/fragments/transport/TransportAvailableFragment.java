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
import com.caturindo.activities.TransportDetailActivity;
import com.caturindo.adapters.TransportItemAdapter;
import com.caturindo.models.TransportItemModel;

import java.util.ArrayList;

public class TransportAvailableFragment extends Fragment implements TransportItemAdapter.ItemListener{

    private View rootView;
    private TransportItemAdapter adapter;
    private RecyclerView rvTransport;
    private ArrayList<TransportItemModel> itemlist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_transport_available, null);

        rvTransport = rootView.findViewById(R.id.rvTransportAvailable);
        itemlist = new ArrayList<TransportItemModel>();
        itemlist.add(new TransportItemModel(1, "Toyota Avanza", 1, 7, R.drawable.ic_launcher_foreground));
        itemlist.add(new TransportItemModel(2, "Daihatsu Xenia", 1, 7, R.drawable.ic_launcher_foreground));
        itemlist.add(new TransportItemModel(3, "Toyota Rush", 1, 7, R.drawable.ic_launcher_foreground));
        itemlist.add(new TransportItemModel(4, "Daihatsu Terios", 1, 7, R.drawable.ic_launcher_foreground));
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
