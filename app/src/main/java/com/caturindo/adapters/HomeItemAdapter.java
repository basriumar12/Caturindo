package com.caturindo.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.caturindo.R;
import com.caturindo.models.HomeItemModel;

import java.util.ArrayList;

public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.HomeViewHolder> {
    private ArrayList<HomeItemModel> mValues;
    private Context mContext;
    private ItemListener mListener;

    public HomeItemAdapter(Context context, ArrayList values, ItemListener itemListener) {

        mValues = values;
        mContext = context;
        mListener=itemListener;
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textView;
        public ImageView imageView;
        public RelativeLayout relativeLayout;
        HomeItemModel item;

        public HomeViewHolder(View v) {
            super(v);

            v.setOnClickListener(this);
            textView = (TextView) v.findViewById(R.id.tvHomeItemName);
            imageView = (ImageView) v.findViewById(R.id.ivHomeItemImage);
            relativeLayout = (RelativeLayout) v.findViewById(R.id.rlHomeItem);
        }

        public void setData(HomeItemModel item) {
            this.item = item;

            textView.setText(item.getText());
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
    public HomeItemAdapter.HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        holder.setData(mValues.get(position));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public interface ItemListener {
        void onItemClick(HomeItemModel item);
    }
}