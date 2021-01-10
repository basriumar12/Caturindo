package com.caturindo.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.caturindo.R;

import java.util.ArrayList;

public class TransportDetailItemAdapter extends RecyclerView.Adapter<TransportDetailItemAdapter.TransportDetailViewHolder>{
    private ArrayList<String> image = new ArrayList<>();
    private Activity activity;

    public TransportDetailItemAdapter(ArrayList<String> image, Activity activity) {
        this.image = image;
        this.activity = activity;
    }

    @NonNull
    @Override
    public TransportDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_detail_transport, parent, false);
        return new TransportDetailItemAdapter.TransportDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransportDetailViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return image.size();
    }

    class TransportDetailViewHolder extends RecyclerView.ViewHolder {

        public TransportDetailViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
