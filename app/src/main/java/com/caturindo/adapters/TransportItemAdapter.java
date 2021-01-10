package com.caturindo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.caturindo.R;
import com.caturindo.models.TransportItemModel;

import java.util.ArrayList;

public class TransportItemAdapter extends RecyclerView.Adapter<TransportItemAdapter.TransportViewHolder> {
    private ArrayList<TransportItemModel> mValues;
    private Context mContext;
    private ItemListener mListener;

    public TransportItemAdapter(Context context, ArrayList values, ItemListener itemListener) {

        mValues = values;
        mContext = context;
        mListener = itemListener;
    }

    public class TransportViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView carName, carAvaibilityValue, carCapacityValue;
        public ImageView imageView;
        public RelativeLayout relativeLayout;
        TransportItemModel item;

        public TransportViewHolder(View v) {
            super(v);

            v.setOnClickListener(this);
            carName = (TextView) v.findViewById(R.id.tvTransportItemNameCar);
            carAvaibilityValue= (TextView) v.findViewById(R.id.tvTransportItemNameAvailable);
            carCapacityValue = (TextView) v.findViewById(R.id.tvTransportItemNameCapacity);
            imageView = (ImageView) v.findViewById(R.id.ivTransportItemImage);
            relativeLayout = (RelativeLayout) v.findViewById(R.id.rlTransportItem);
        }

        public void setData(TransportItemModel item) {
            this.item = item;

            carName.setText(item.getCarName());
            if(item.getAvailability() == 1){
                carAvaibilityValue.setText("Available");
            } else {
                carAvaibilityValue.setText("Booked");
            }
            carCapacityValue.setText(String.valueOf(item.getCapacity()));
            imageView.setImageResource(item.getDrawable());

        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }

    @Override
    public TransportItemAdapter.TransportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_transport, parent, false);
        return new TransportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TransportViewHolder holder, int position) {
        holder.setData(mValues.get(position));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public interface ItemListener {
        void onItemClick(TransportItemModel item);
    }
}