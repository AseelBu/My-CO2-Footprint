package com.androidcourse.energyconsumptiondiary_androidapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.TransportationListAdapter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.ElectricalHouseSupplies;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Transportation;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;

import java.util.ArrayList;

public class AddingItemActivitya extends AppCompatActivity {
    public static final String TAG = "AddingItemActivitya";
    public EditText name = null;
    public EditText CO2Amount= null;
    private Context context;
    private DataHolder dh = DataHolder.getInstance();
    private SharedPreferences prefs = null;
    private ElectricalHouseSupplies adapter;
    private Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_itema);
        name=(EditText)findViewById(R.id.typea);
        CO2Amount=(EditText)findViewById(R.id.amountta);
        btn=(Button)findViewById(R.id.edititem2a);

        context=this;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClicked2();
            }
        });
    }


    public void addClicked2()
    {
        try {
            if ((TextUtils.isEmpty(name.getText().toString())==false))
            {
//                ArrayList<Transportation> tlist=dh.getTransportation();
//                dh.addTransportaion(name.getText().toString(),fuelType.getText().toString(),Integer.parseInt(CO2Amount.getText().toString()));
//                ad.getList().notifyDataSetChanged();
                Toast.makeText(context,
                        "add successfully",
                        Toast.LENGTH_SHORT).show();
                Log.d("before",(CO2Amount.getText().toString()));
//                int x=Integer.parseInt(CO2Amount.getText().toString());
                Intent intent = new Intent();
                ElectricalHouseSupplies t=new ElectricalHouseSupplies(name.getText().toString(),Integer.parseInt(CO2Amount.getText().toString()));
                intent.putExtra("data",t);
                setResult(RESULT_OK,intent);
                finish();


            }
        } catch (NumberFormatException exception) {

        }




    }
}