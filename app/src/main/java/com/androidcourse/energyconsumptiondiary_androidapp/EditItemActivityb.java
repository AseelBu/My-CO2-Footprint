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
import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.FoodListAdapter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Food;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;

public class EditItemActivityb extends AppCompatActivity {
    public static final String TAG = "EditItemActivityb";
    public EditText name = null;
    public EditText fuelType = null;
    public EditText CO2Amount= null;
    private Context context;
    private DataHolder dh = DataHolder.getInstance();
    private SharedPreferences prefs = null;
    private Food adapter;
    private Button btn;
    private FoodListAdapter ad;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_itemb);
        name=(EditText)findViewById(R.id.typeeditb);
        CO2Amount=(EditText)findViewById(R.id.amounteditb);
        btn=(Button)findViewById(R.id.editBtnb);
        Intent intent = getIntent();
        if(intent!=null) {
            name.setText(intent.getStringExtra("Typeb"));
            String x=intent.getStringExtra("CO2Amountb");
            Log.d("tttttttttttttttt",x);
            CO2Amount.setText(x);
            id=intent.getIntExtra("positionb",-1);
        }
        context=this;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClicked2();
            }
        });
    }
    //add new item instead of the old item on food list
    public void addClicked2()
    {
        try {
            if (TextUtils.isEmpty(name.getText().toString())==false){
                Toast.makeText(context,
                        "save successfully",
                        Toast.LENGTH_SHORT).show();
                Intent intent =new Intent();
                Food t=new Food(name.getText().toString(),Integer.parseInt(CO2Amount.getText().toString()));
                intent.putExtra("positionb",id);
                intent.putExtra("data",t);
                setResult(RESULT_OK,intent);
                finish();
            }
        } catch (NumberFormatException exception) {
        }
    }
}