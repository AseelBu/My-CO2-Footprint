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
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Services;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;

public class AddingItemActivityc extends AppCompatActivity {
    public static final String TAG = "AddingItemActivityc";
    public EditText name = null;
    public EditText CO2Amount= null;
    private Context context;
    private DataHolder dh = DataHolder.getInstance();
    private SharedPreferences prefs = null;
    private Services adapter;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_itemc);
        name=(EditText)findViewById(R.id.typec);
        CO2Amount=(EditText)findViewById(R.id.amounttc);
        btn=(Button)findViewById(R.id.edititem2c);
        context=this;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClicked2();
            }
        });
    }
    //add item to services list
    public void addClicked2()
    {
        try {
            if ((TextUtils.isEmpty(name.getText().toString())==false))
            {
                Toast.makeText(context,
                        "add successfully",
                        Toast.LENGTH_SHORT).show();
                Log.d("before",(CO2Amount.getText().toString()));
                Intent intent = new Intent();
                Services t=new Services(name.getText().toString(),Integer.parseInt(CO2Amount.getText().toString()));
                Log.d("after",String.valueOf(t.getCO2Amount()));
                intent.putExtra("data", t);
                setResult(RESULT_OK,intent);
                finish();
            }
        } catch (NumberFormatException exception) {

        }
    }
}