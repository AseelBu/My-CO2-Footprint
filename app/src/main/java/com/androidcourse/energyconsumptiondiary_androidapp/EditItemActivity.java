package com.androidcourse.energyconsumptiondiary_androidapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.TransportationListAdapter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Transportation;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;

public class EditItemActivity extends AppCompatActivity {
    public static final String TAG = "EditItemActivity";
    public EditText name = null;
    public EditText fuelType = null;
    public EditText CO2Amount= null;
    private Context context;
    private DataHolder dh = DataHolder.getInstance();
    private SharedPreferences prefs = null;
    private  TransportationListAdapter adapter;
    private Button btn;
    private TransportationListAdapter ad;
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        name=(EditText)findViewById(R.id.typeedit);
        fuelType=(EditText)findViewById(R.id.fueledit);
        CO2Amount=(EditText)findViewById(R.id.amountedit);
        btn=(Button)findViewById(R.id.editBtn);
        Intent intent = getIntent();
        if(intent!=null) {
            name.setText(intent.getStringExtra("Type"));
//            Log.d("ssss",String.valueOf(intent.getIntExtra("CO2Amount",0)));
            fuelType.setText(intent.getStringExtra("FuelType"));
//            Log.d("eeeeeeeeeeeeeeee",String.valueOf(intent.getStringExtra("FuelType")));
            String x=intent.getStringExtra("CO2Amount");
            Log.d("tttttttttttttttt",x);
            CO2Amount.setText(x);
            id=intent.getIntExtra("position",-1);

        }

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
            if ((TextUtils.isEmpty(name.getText().toString())==false) ||
                    (TextUtils.isEmpty(fuelType.getText().toString())==false))
            {
//                ArrayList<Transportation> tlist=dh.getTransportation();
//                tlist.add(new Transportation(name.getText().toString(),fuelType.getText().toString(),Integer.parseInt(CO2Amount.getText().toString())));
//                dh.setTransportation(tlist);
//                adapter.notifyDataSetChanged();

                Toast.makeText(context,
                        "save successfully",
                        Toast.LENGTH_SHORT).show();
//                ad.notifyDataSetChanged();
                Intent intent =new Intent();
                Transportation t=new Transportation(name.getText().toString(),fuelType.getText().toString(),Integer.parseInt(CO2Amount.getText().toString()));
                intent.putExtra("position",id);
                intent.putExtra("data",t);
                setResult(RESULT_OK,intent);
                finish();

//                startActivity(intent);
            }
        } catch (NumberFormatException exception) {

        }




    }
}