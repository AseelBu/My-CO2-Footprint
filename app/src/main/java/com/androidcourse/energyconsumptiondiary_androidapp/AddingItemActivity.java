package com.androidcourse.energyconsumptiondiary_androidapp;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.androidcourse.energyconsumptiondiary_androidapp.Model.Co2Impacter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.ElectricalHouseSupplies;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Food;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Service;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Transportation;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;
import com.androidcourse.energyconsumptiondiary_androidapp.core.ImpactType;

import java.io.FileNotFoundException;
import java.io.IOException;

public class AddingItemActivity extends AppCompatActivity {
    public static final String TAG = "AddingItemActivity";
    private static final String IMPACTERTYPE = "ImpacterType";
    public static final int REQUEST_IMAGE_GET = 3;

    private ImpactType impacterType;
    private Co2Impacter impacter=null;

    private TextView title;
    public EditText name = null;
    public EditText fuelType = null;
    public EditText co2Amount= null;
    public EditText Question= null;

    private Context context;
    private final DataHolder dh = DataHolder.getInstance();
    private SharedPreferences prefs = null;
    private Button addBtn;
    private ImageButton uploadImgBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_item);
        title=(TextView)findViewById(R.id.addingActivityTitle);
        name=(EditText)findViewById(R.id.type);
        fuelType=(EditText)findViewById(R.id.fuel3);
        co2Amount=(EditText)findViewById(R.id.amountt);
        Question=(EditText)findViewById(R.id.Question);


        Intent intent = getIntent();
        if(intent!=null) {
            impacterType= ImpactType.valueOf(intent.getStringExtra(IMPACTERTYPE));
            title.setText("Add "+impacterType.name().toLowerCase());
            createImpacter();

            //remove fuel type field if not transportation
            if(!impacterType.equals(ImpactType.TRANSPORTATIOIN)){
                fuelType.setVisibility(View.GONE);
            }

        }else{
            finish();
        }


        addBtn=(Button)findViewById(R.id.edititem2);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBtnClicked();
            }
        });
        uploadImgBtn =(ImageButton) findViewById(R.id.upload);
        uploadImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//                startActivityForResult(intent, REQUEST_IMAGE_GET);
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_IMAGE_GET);
                }
            }
        });

        context=this;

        //action bar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //get picture from phone gallery
        if(requestCode== REQUEST_IMAGE_GET && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                impacter.setImg(bitmap);
                uploadImgBtn.setImageDrawable(new BitmapDrawable(getResources(), bitmap));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    //builder for the impacter by type
    private void createImpacter() {
        switch (impacterType){
            case TRANSPORTATIOIN:
                impacter= new Transportation();
                break;
            case FOOD:
                impacter= new Food();
                break;
            case ELECTRICAL:
                impacter= new ElectricalHouseSupplies();
                break;
            case SERVICES:
                impacter= new Service();
                break;
        }
    }


    //add item to the data holder
    public void addBtnClicked()
    {
        try {
            //if the input is empty
            if ((!TextUtils.isEmpty(name.getText().toString())) ||(!TextUtils.isEmpty(Question.getText().toString()))||
                    (!TextUtils.isEmpty(fuelType.getText().toString())))
            {
//                setting data in impacter
                impacter.setName(name.getText().toString());
                impacter.setQuestion(Question.getText().toString());
                if(impacterType.equals(ImpactType.TRANSPORTATIOIN)){
                    ((Transportation)impacter).setFuelType(fuelType.getText().toString());
                }
                impacter.setCo2Amount(Integer.parseInt(co2Amount.getText().toString()));

                dh.addImpacter(impacterType,impacter);
                Intent intent = new Intent();

                //Transportation2 t=new Transportation2(name.getText().toString(),fuelType.getText().toString(),Integer.parseInt(CO2Amount.getText().toString()),uploadImgBtn.getDrawable());

                setResult(RESULT_OK,intent);
                Toast.makeText(context,
                        "add successfully",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (NumberFormatException exception) {

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return false;
    }
}