package com.androidcourse.energyconsumptiondiary_androidapp;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.ElectricListAdapter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.ElectricalHouseSupplies;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;

public class EditItemActivitya extends AppCompatActivity {
    public static final String TAG = "EditItemActivitya";
    public EditText name = null;
    public EditText fuelType = null;
    public EditText CO2Amount= null;
    private Context context;
    private DataHolder dh = DataHolder.getInstance();
    private SharedPreferences prefs = null;
    private ElectricalHouseSupplies adapter;
    private Button btn;
    private ElectricListAdapter ad;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_itema);
        name=(EditText)findViewById(R.id.typeedita);
        CO2Amount=(EditText)findViewById(R.id.amountedita);
        btn=(Button)findViewById(R.id.editBtna);
        Intent intent = getIntent();
        if(intent!=null) {
            name.setText(intent.getStringExtra("Typea"));
            String x=intent.getStringExtra("CO2Amounta");
            CO2Amount.setText(x);
            id=intent.getIntExtra("positiona",-1);
        }
        context=this;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClicked2();
            }
        });
    }
    //add new item instead of the old item on electric list
    public void addClicked2()
    {
        try {
            if (TextUtils.isEmpty(name.getText().toString())==false){
                Toast.makeText(context,
                        "save successfully",
                        Toast.LENGTH_SHORT).show();
                Intent intent =new Intent();
                ElectricalHouseSupplies t=new ElectricalHouseSupplies(name.getText().toString(),Integer.parseInt(CO2Amount.getText().toString()));
                intent.putExtra("positiona",id);
                intent.putExtra("data",t);
                setResult(RESULT_OK,intent);
                finish();
            }
        } catch (NumberFormatException exception) {

        }
    }
}