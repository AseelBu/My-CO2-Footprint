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
import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.ServicesListAdapter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Services;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;

public class EditItemActivityc extends AppCompatActivity {
    public static final String TAG = "EditItemActivityc";
    public EditText name = null;
    public EditText fuelType = null;
    public EditText CO2Amount= null;
    private Context context;
    private DataHolder dh = DataHolder.getInstance();
    private SharedPreferences prefs = null;
    private Services adapter;
    private Button btn;
    private ServicesListAdapter ad;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_itemc);
        name=(EditText)findViewById(R.id.typeeditc);
        CO2Amount=(EditText)findViewById(R.id.amounteditc);
        btn=(Button)findViewById(R.id.editBtnc);
        Intent intent = getIntent();
        if(intent!=null) {
            name.setText(intent.getStringExtra("Typec"));
            String x=intent.getStringExtra("CO2Amountc");
            CO2Amount.setText(x);
            id=intent.getIntExtra("positionc",-1);
        }
        context=this;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClicked2();
            }
        });
    }
    //add new item instead of the old item on services list
    public void addClicked2()
    {
        try {
            if (TextUtils.isEmpty(name.getText().toString())==false){
                Toast.makeText(context,
                        "save successfully",
                        Toast.LENGTH_SHORT).show();
                Intent intent =new Intent();
                Services t=new Services(name.getText().toString(),Integer.parseInt(CO2Amount.getText().toString()));
                intent.putExtra("positionc",id);
                intent.putExtra("data", t);
                setResult(RESULT_OK,intent);
                finish();
            }
        } catch (NumberFormatException exception) {

        }
    }
}