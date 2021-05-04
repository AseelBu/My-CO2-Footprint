package com.androidcourse.energyconsumptiondiary_androidapp.Adapters;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import com.androidcourse.energyconsumptiondiary_androidapp.EditItemActivity;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Transportation;
import com.androidcourse.energyconsumptiondiary_androidapp.R;
import com.androidcourse.energyconsumptiondiary_androidapp.TransportationMain;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;
import java.util.List;

public class TransportationListAdapter extends ArrayAdapter<Transportation> {
    private static final int REQ_CODE = 123;
    public static final int GET_FROM_GALLERY = 3;
    private DataHolder dh = DataHolder.getInstance();
    private List<Transportation> dataList = null;
    private Context context = null;
   //TransportationListAdapter Constructor
    public TransportationListAdapter(Context context, List<Transportation> dataList) {
        super(context, R.layout.transportation_list, dataList);
        this.dataList = dataList;
        this.dataList.add(0,new Transportation());
        this.context = context;
    }
    //get the size of datalist
    @Override
    public int getCount() {
        return dataList.size();
    }
     //return the list from Transportation type
    public List<Transportation> getList()
    {
        return this.dataList ;
    }
    //add item to the list
    public void addToList(Transportation e)
    {
        dataList.add(e);
    }
    //remove item from the list
    public void removeFromList(int e)
    {
        dataList.remove(e);
    }
    //get item method
    @Override
    public Transportation getItem(int position)
    {
        return dataList.get(position);
    }
    //get view method
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView;
        if (position == 0) {
            rowView = inflater.inflate(R.layout.listheader_transportation, null, false);
            ImageView add = (ImageView) rowView.findViewById(R.id.adding);
        } else {
            rowView=inflater.inflate(R.layout.transportation_list, null, false);
            TextView txtTitle2 = (TextView) rowView.findViewById(R.id.Fueltype);
            TextView txtTitle3 = (TextView) rowView.findViewById(R.id.Co2Amount);
            ImageView edit = (ImageView) rowView.findViewById(R.id.edit);
            ImageView delete = (ImageView) rowView.findViewById(R.id.delete2);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.transportation2);
            final Transportation itemInfo = dataList.get(position);
            txtTitle2.setText(itemInfo.getFuelType());
            txtTitle3.setText(String.valueOf(itemInfo.getCO2Amount()));
            imageView.setImageDrawable(itemInfo.getImg());
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete2(position);
                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit2(position);
            }
            });
            }
            return rowView;
        }
        //delete item from the list
    public void delete2(int position) {
        new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_delete)
                .setTitle("Are you sure ?")
                .setMessage("Do you want to delete this item")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dataList.remove(position);
                        notifyDataSetChanged();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
          //edit item from the list
        public void edit2(int position) {
              Intent intent = new Intent(context, EditItemActivity.class);
            intent.putExtra("position",position);
            intent.putExtra("Type",dataList.get(position).getName());
              intent.putExtra("FuelType",dataList.get(position).getFuelType());
              intent.putExtra("CO2Amount",(String.valueOf(dataList.get(position).getCO2Amount())));
            ((TransportationMain)context).startActivityForResult(intent,REQ_CODE);
        }
}
