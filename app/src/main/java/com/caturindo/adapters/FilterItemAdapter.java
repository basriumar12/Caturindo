package com.caturindo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.caturindo.R;
import com.caturindo.models.HomeItemModel;
import com.caturindo.preference.Prefuser;

import java.util.ArrayList;
import java.util.HashMap;

public class FilterItemAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ItemListener mListener;
    public interface ItemListener {
        void onItemClick(String item);
    }

    private ArrayList<String> years;

    private HashMap<String, ArrayList<String>> listDataChild;

    public FilterItemAdapter(Context context, ArrayList<String> years,ItemListener itemListener) {
        this.context = context;
        this.years = years;
        this.mListener = itemListener;
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

        int tahun = i;
        if (i == 0){
            tahun = 2021;
        }else if (i == 1){
            tahun = 2022;
        }else if (i == 2){
            tahun = 2023;
        }else if (i == 3){
            tahun = 2024;
        }else if (i == 4){
            tahun = 2025;
        }

        TextView januari = view.findViewById(R.id.januari);
        TextView feb = view.findViewById(R.id.februari);
        TextView mar = view.findViewById(R.id.maret);
        TextView april = view.findViewById(R.id.april);
        TextView mei = view.findViewById(R.id.mei);
        TextView juni = view.findViewById(R.id.juni);
        TextView juli = view.findViewById(R.id.juli);
        TextView agus = view.findViewById(R.id.agusutus);
        TextView sep = view.findViewById(R.id.september);
        TextView okt = view.findViewById(R.id.oktober);
        TextView nov = view.findViewById(R.id.november);
        TextView des = view.findViewById(R.id.desember);
        int finalTahun = tahun;
        januari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              new Prefuser().setCarruntDate(String.valueOf(finalTahun)+"-"+"01");
                if (mListener != null) {
                    mListener.onItemClick("item");
                }
                Toast.makeText(context,januari.getText().toString(),Toast.LENGTH_LONG).show();
            }
        });


        feb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Prefuser().setCarruntDate(String.valueOf(finalTahun)+"-"+"02");
                if (mListener != null) {
                    mListener.onItemClick("item");
                }
                Toast.makeText(context,feb.getText().toString()+" "+finalTahun,Toast.LENGTH_LONG).show();
            }
        });

        mar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Prefuser().setCarruntDate(String.valueOf(finalTahun)+"-"+"03");
                if (mListener != null) {
                    mListener.onItemClick("item");
                }

                Toast.makeText(context,mar.getText().toString()+" "+finalTahun,Toast.LENGTH_LONG).show();
            }
        });

        april.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Prefuser().setCarruntDate(String.valueOf(finalTahun)+"-"+"04");
                if (mListener != null) {
                    mListener.onItemClick("item");
                }
                Toast.makeText(context,april.getText().toString()+" "+finalTahun,Toast.LENGTH_LONG).show();
            }
        });

        mei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Prefuser().setCarruntDate(String.valueOf(finalTahun)+"-"+"05");
                if (mListener != null) {
                    mListener.onItemClick("item");
                }
                Toast.makeText(context,mei.getText().toString()+" "+finalTahun,Toast.LENGTH_LONG).show();
            }
        });

        juni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Prefuser().setCarruntDate(String.valueOf(finalTahun)+"-"+"06");
                if (mListener != null) {
                    mListener.onItemClick("item");
                }
                Toast.makeText(context,juni.getText().toString()+" "+finalTahun,Toast.LENGTH_LONG).show();
            }
        });

        juli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Prefuser().setCarruntDate(String.valueOf(finalTahun)+"-"+"07");
                if (mListener != null) {
                    mListener.onItemClick("item");
                }
                Toast.makeText(context,juli.getText().toString()+" "+finalTahun,Toast.LENGTH_LONG).show();
            }
        });

        agus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Prefuser().setCarruntDate(String.valueOf(finalTahun)+"-"+"08");
                if (mListener != null) {
                    mListener.onItemClick("item");
                }
                Toast.makeText(context,agus.getText().toString()+" "+finalTahun,Toast.LENGTH_LONG).show();
            }
        });

        sep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Prefuser().setCarruntDate(String.valueOf(finalTahun)+"-"+"09");
                if (mListener != null) {
                    mListener.onItemClick("item");
                }

                Toast.makeText(context,sep.getText().toString()+" "+finalTahun,Toast.LENGTH_LONG).show();
            }
        });

        okt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Prefuser().setCarruntDate(String.valueOf(finalTahun)+"-"+"10");
                if (mListener != null) {
                    mListener.onItemClick("item");
                }
                Toast.makeText(context,okt.getText().toString()+" "+finalTahun,Toast.LENGTH_LONG).show();
            }
        });

        nov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Prefuser().setCarruntDate(String.valueOf(finalTahun)+"-"+"11");
                if (mListener != null) {
                    mListener.onItemClick("item");
                }
                Toast.makeText(context,nov.getText().toString()+" "+finalTahun,Toast.LENGTH_LONG).show();
            }
        });

        des.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Prefuser().setCarruntDate(String.valueOf(finalTahun)+"-"+"12");
                if (mListener != null) {
                    mListener.onItemClick("item");
                }
                Toast.makeText(context,des.getText().toString()+" "+finalTahun,Toast.LENGTH_LONG).show();
            }
        });


        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
