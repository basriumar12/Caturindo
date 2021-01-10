package com.caturindo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.caturindo.R;

import java.util.ArrayList;
import java.util.HashMap;

public class FilterItemAdapter extends BaseExpandableListAdapter {
    private Context context;

    private ArrayList<String> years;

    private HashMap<String, ArrayList<String>> listDataChild;

    public FilterItemAdapter(Context context, ArrayList<String> years) {
        this.context = context;
        this.years = years;
    }

    @Override
    public int getGroupCount() {
        return years.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return this.years.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return "test";
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String) getGroup(i);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_filter_group, null);
        }
        TextView title = view.findViewById(R.id.tv_group_title);
        title.setText(headerTitle);

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_filter_child, null);
        }

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
