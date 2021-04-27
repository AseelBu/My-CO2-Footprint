package com.androidcourse.energyconsumptiondiary_androidapp;



import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class CustomListAdapter extends ArrayAdapter<ItemInfo> {

    private List<ItemInfo> dataList = null;
    private Context context = null;
    public CustomListAdapter(Context context, List<ItemInfo> dataList) {
        super(context, R.layout.mylist, dataList);
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public ItemInfo getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public View getView( int position, View view, ViewGroup parent) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View rowView=inflater.inflate(R.layout.mylist, null,false);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.itemInfo);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.extratext);
        final ItemInfo itemInfo = dataList.get(position);
        txtTitle.setText(itemInfo.getName());
        imageView.setImageResource(itemInfo.getImgId());
        extratxt.setText("Description "+ itemInfo.getName());

//		imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//				CustomListAdapter.this.remove(itemInfo);
//            }
//        });
        return rowView;

    };
}
