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
import com.caturindo.models.RoomItemModel;

import java.util.ArrayList;

public class RoomItemAdapter extends RecyclerView.Adapter<RoomItemAdapter.TransportViewHolder> {
    private ArrayList<RoomItemModel> mValues;
    private Context mContext;
    private ItemListener mListener;

    public RoomItemAdapter(Context context, ArrayList values, ItemListener itemListener) {

        mValues = values;
        mContext = context;
        mListener = itemListener;
    }

    public class TransportViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView roomName, roomAvaibilityValue, roomCapacityValue;
        public ImageView imageView;
        public ImageView availabilityImageView;
        public RelativeLayout relativeLayout;
        RoomItemModel item;

        public TransportViewHolder(View v) {
            super(v);

            v.setOnClickListener(this);
            roomName = (TextView) v.findViewById(R.id.tvRoomItemNameRoom);
            roomAvaibilityValue= (TextView) v.findViewById(R.id.tvRoomItemNameAvailable);
            roomCapacityValue = (TextView) v.findViewById(R.id.tvRoomItemNameCapacity);
            imageView = (ImageView) v.findViewById(R.id.ivRoomItemImage);
            availabilityImageView = v.findViewById(R.id.ivRoomItemImageAvailable);
            relativeLayout = (RelativeLayout) v.findViewById(R.id.rlRoomItem);
        }

        public void setData(RoomItemModel item) {
            this.item = item;

            roomName.setText(item.getRoomName());
            if(item.getAvailability() == 1){
                roomAvaibilityValue.setText("Available");
                availabilityImageView.setImageResource(R.drawable.ic_icon_unchecked);
            } else {
                roomAvaibilityValue.setText("Booked");
                availabilityImageView.setImageResource(R.drawable.ic_icon_checked);
            }
            roomCapacityValue.setText(String.valueOf(item.getCapacity()));
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
    public RoomItemAdapter.TransportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_room, parent, false);
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
        void onItemClick(RoomItemModel item);
    }
}