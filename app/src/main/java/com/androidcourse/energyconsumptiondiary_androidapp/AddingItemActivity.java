package com.androidcourse.energyconsumptiondiary_androidapp;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.TransportationListAdapter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Transportation;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AddingItemActivity extends AppCompatActivity {
    public static final String TAG = "AddingItemActivity";
    public EditText name = null;
    public EditText fuelType = null;
    public EditText CO2Amount= null;
    private Context context;
    private DataHolder dh = DataHolder.getInstance();
    private SharedPreferences prefs = null;
    private  TransportationListAdapter adapter;
    private Button btn;
    private ImageButton upload;
    public static final int GET_FROM_GALLERY = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_item);
        name=(EditText)findViewById(R.id.type);
        fuelType=(EditText)findViewById(R.id.fuel3);
        CO2Amount=(EditText)findViewById(R.id.amountt);
        btn=(Button)findViewById(R.id.edititem2);
        upload=(ImageButton) findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, GET_FROM_GALLERY);
            }
        });
        context=this;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClicked2();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                upload.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    //add item to the transportation list
    public void addClicked2()
    {
        try {
            //if the input is empty
            if ((TextUtils.isEmpty(name.getText().toString())==false) ||
                    (TextUtils.isEmpty(fuelType.getText().toString())==false))
            {
                Toast.makeText(context,
                        "add successfully",
                        Toast.LENGTH_SHORT).show();
                Log.d("before",(CO2Amount.getText().toString()));
                Intent intent = new Intent();
                Transportation t=new Transportation(name.getText().toString(),fuelType.getText().toString(),Integer.parseInt(CO2Amount.getText().toString()),upload.getDrawable());
                intent.putExtra("data",t);
                setResult(RESULT_OK,intent);
                finish();
            }
        } catch (NumberFormatException exception) {

        }
    }
}