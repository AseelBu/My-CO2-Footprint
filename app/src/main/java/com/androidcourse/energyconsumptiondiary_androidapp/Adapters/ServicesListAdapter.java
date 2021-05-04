package com.androidcourse.energyconsumptiondiary_androidapp.Adapters;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import com.androidcourse.energyconsumptiondiary_androidapp.AccountSettingsActivity;
import com.androidcourse.energyconsumptiondiary_androidapp.AddingItemActivity;
import com.androidcourse.energyconsumptiondiary_androidapp.EditItemActivity;
import com.androidcourse.energyconsumptiondiary_androidapp.EditItemActivityc;
import com.androidcourse.energyconsumptiondiary_androidapp.ElectricMain;
import com.androidcourse.energyconsumptiondiary_androidapp.FoodMain;
import com.androidcourse.energyconsumptiondiary_androidapp.LogInActivity;
import com.androidcourse.energyconsumptiondiary_androidapp.MainActivity;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.ElectricalHouseSupplies;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Food;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Services;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Transportation;
import com.androidcourse.energyconsumptiondiary_androidapp.R;
import com.androidcourse.energyconsumptiondiary_androidapp.ServicesMain;
import com.androidcourse.energyconsumptiondiary_androidapp.SignUpActivity;
import com.androidcourse.energyconsumptiondiary_androidapp.TransportationMain;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static androidx.core.app.ActivityCompat.startActivityForResult;


public class ServicesListAdapter extends ArrayAdapter<Services> {
    private static final int REQ_CODE = 123;
    private DataHolder dh = DataHolder.getInstance();
    private List<Services> dataList = null;
    private Context context = null;

    public ServicesListAdapter(Context context, List<Services> dataList) {
        super(context, R.layout.services_list, dataList);
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    public List<Services> getList() {
        return this.dataList ;
    }
    public void addToList(Services e) {
        dataList.add(e);
    }
    public void removeFromList(int e) {
        dataList.remove(e);
    }
    @Override
    public Services getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView;
        if (position == 0) {
            rowView = inflater.inflate(R.layout.listheader_services, null, false);
            ImageView add = (ImageView) rowView.findViewById(R.id.adding);


        } else {
            rowView=inflater.inflate(R.layout.services_list, null, false);


            TextView txtTitle3 = (TextView) rowView.findViewById(R.id.Co2Amountc);
            ImageView edit = (ImageView) rowView.findViewById(R.id.editc);
            ImageView delete = (ImageView) rowView.findViewById(R.id.delete2c);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.services2);
            final Services itemInfo = dataList.get(position);
            txtTitle3.setText(String.valueOf(itemInfo.getCO2Amount()));
            imageView.setImageResource(itemInfo.getImg());
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
                .setNegativeButton("No",null)
                .show();


    }

    public void edit2(int position) {
        Intent intent = new Intent(context, EditItemActivityc.class);
        intent.putExtra("positionc",position);
        intent.putExtra("Typec",dataList.get(position).getName());
        intent.putExtra("CO2Amountc",(String.valueOf(dataList.get(position).getCO2Amount())));
        ((ServicesMain)context).startActivityForResult(intent,REQ_CODE);


    }

}
