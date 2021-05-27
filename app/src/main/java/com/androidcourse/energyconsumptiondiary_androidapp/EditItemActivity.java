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
import android.speech.tts.TextToSpeech;
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
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Transportation;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;
import com.androidcourse.energyconsumptiondiary_androidapp.core.ImpactType;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;

public class EditItemActivity extends AppCompatActivity {
    public static final String TAG = "EditItemActivity";
    private static final String IMPACTERTYPE = "ImpacterType";
    public static final int REQUEST_IMAGE_GET = 3;

    public  final DataHolder dh = DataHolder.getInstance();

    private TextView title;
    private ImageButton imageUpload;
    private EditText name = null;
    private EditText fuelType = null;
    private EditText Question = null;
    private EditText co2Amount = null;
    private Context context;
    private SharedPreferences prefs = null;
//    private  TransportationListAdapter adapter;
    private Button editBtn;
    private int id;
    private ImpactType impacterType;
    private Co2Impacter impacter;
    TextToSpeech t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_edit_item);
        title=(TextView)findViewById(R.id.editingActivityTitle);

        name=(EditText)findViewById(R.id.typeedit);
        Question=(EditText)findViewById(R.id.Question2);
        fuelType=(EditText)findViewById(R.id.fueledit);
        co2Amount=(EditText)findViewById(R.id.amountedit);
        editBtn=(Button)findViewById(R.id.editBtn);
        imageUpload=(ImageButton) findViewById(R.id.uploadImgEdit);
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
        imageUpload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_IMAGE_GET);
                }
            }
        });
        Intent intent = getIntent();
        if(intent!=null) {
            id= intent.getIntExtra("id",-1);
            impacterType= ImpactType.valueOf(intent.getStringExtra(IMPACTERTYPE));
            impacter=dh.getImpacterByid(impacterType, id);
            setData(impacter,impacterType);
            title.setText("Edit "+impacterType.name().toLowerCase());

        }else{
            finish();
        }

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editClicked();
            }
        });

        //action bar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }
    //add new item instead of the old item in data list
    public void editClicked() {
        if (impacterType.equals(ImpactType.TRANSPORTATIOIN)) {
            if ((TextUtils.isEmpty(name.getText().toString())) || (TextUtils.isEmpty(Question.getText().toString())) ||
                    (TextUtils.isEmpty(co2Amount.getText().toString())) || (TextUtils.isEmpty(fuelType.getText().toString()))) {
//                Toast.makeText(context,
//                        "add failed..There are empty input!",
//                        Toast.LENGTH_SHORT).show();
                String toSpeak = "add failed..There are empty input!";
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

            } else {
                try {
                    if ((!TextUtils.isEmpty(name.getText().toString())) || (!TextUtils.isEmpty(Question.getText().toString())) ||
                            (!TextUtils.isEmpty(fuelType.getText().toString()))) {
                        impacter.setName(name.getText().toString());
                        impacter.setQuestion(Question.getText().toString());
                            ((Transportation) impacter).setFuelType(fuelType.getText().toString());
                        impacter.setCo2Amount(Integer.parseInt(co2Amount.getText().toString()));
                        dh.removeImpacter(impacterType, impacter);
                        dh.addImpacter(impacterType, impacter);
                        Intent intent = new Intent();
//                Transportation2 t=new Transportation2(name.getText().toString(),"content",Integer.parseInt(co2Amount.getText().toString()),fuelType.getText().toString(),null);
//                intent.putExtra("position",id);
//                intent.putExtra("data",t);
                        setResult(RESULT_OK, intent);
//                        Toast.makeText(context,
//                                "save successfully",
//                                Toast.LENGTH_SHORT).show();
                        String toSpeak = "save successfully";
                        Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                        finish();
                    } else {

                    }
                } catch (NumberFormatException exception) {

                }
            }
        }
        if((impacterType.equals(ImpactType.ELECTRICAL))||(impacterType.equals(ImpactType.SERVICES))||(impacterType.equals(ImpactType.FOOD)))
        {
            if ((TextUtils.isEmpty(name.getText().toString())) || (TextUtils.isEmpty(Question.getText().toString())) ||
                    (TextUtils.isEmpty(co2Amount.getText().toString())) || (TextUtils.isEmpty(fuelType.getText().toString()))) {
//                Toast.makeText(context,
//                        "add failed..There are empty input!",
//                        Toast.LENGTH_SHORT).show();
                String toSpeak = "add failed..There are empty input!";
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

            } else {
                try {
                    if ((!TextUtils.isEmpty(name.getText().toString())) || (!TextUtils.isEmpty(Question.getText().toString()))) {
                        impacter.setName(name.getText().toString());
                        impacter.setQuestion(Question.getText().toString());
                        impacter.setCo2Amount(Integer.parseInt(co2Amount.getText().toString()));
                        dh.removeImpacter(impacterType, impacter);
                        dh.addImpacter(impacterType, impacter);
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
//                        Toast.makeText(context,
//                                "save successfully",
//                                Toast.LENGTH_SHORT).show();
                        String toSpeak = "save successfully";
                        Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                        finish();
                    } else {

                    }
                } catch (NumberFormatException exception) {

                }
            }
        }
    }

    //set data info in view fields
    private void setData(Co2Impacter impacter,ImpactType type){

        name.setText(impacter.getName());
        Question.setText(impacter.getQuestion());
        co2Amount.setText(String.valueOf(impacter.getCo2Amount()));
        imageUpload.setImageDrawable(new BitmapDrawable(context.getResources(), impacter.getImg()));

        if(impacter instanceof Transportation){
            fuelType.setText(((Transportation)impacter).getFuelType());
        }else{
            fuelType.setVisibility(View.GONE);
        }


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
                imageUpload.setImageDrawable(new BitmapDrawable(getResources(), bitmap));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
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