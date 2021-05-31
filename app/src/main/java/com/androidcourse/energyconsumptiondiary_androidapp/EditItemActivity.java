package com.androidcourse.energyconsumptiondiary_androidapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.androidcourse.energyconsumptiondiary_androidapp.Model.Co2Impacter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.MyCo2FootprintManager;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Transportation;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;
import com.androidcourse.energyconsumptiondiary_androidapp.core.ImpactType;
import com.androidcourse.energyconsumptiondiary_androidapp.core.Units;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class EditItemActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String TAG = "EditItemActivity";
    public static final int REQUEST_IMAGE_GET = 3;
    protected static final int NEW_ITEM_TAG = -111;
    private static final String IMPACTERTYPE = "ImpacterType";
    public final DataHolder dh = DataHolder.getInstance();
    private final MyCo2FootprintManager db = MyCo2FootprintManager.getInstance();
    public Spinner spinner;
    TextToSpeech t1;
    private TextView title;
    private ImageButton imageUpload;
    private EditText name = null;
    private EditText fuelType = null;
    private EditText Question = null;
    private EditText co2Amount = null;
    private Context context;
    private SharedPreferences prefs = null;
    private Button editBtn;
    private int id;
    private ImpactType impacterType;
    private Co2Impacter impacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_edit_item);
        title = (TextView) findViewById(R.id.editingActivityTitle);
        spinner = (Spinner) findViewById(R.id.spinner2);
        name = (EditText) findViewById(R.id.typeedit);
        Question = (EditText) findViewById(R.id.Question2);
        fuelType = (EditText) findViewById(R.id.fueledit);
        co2Amount = (EditText) findViewById(R.id.amountedit);
        editBtn = (Button) findViewById(R.id.editBtn);
        imageUpload = (ImageButton) findViewById(R.id.uploadImgEdit);
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        Units[] categories = Units.values();

        // Creating adapter for spinner
        ArrayAdapter<Units> dataAdapter = new ArrayAdapter<Units>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        imageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(Intent.createChooser(intent,"Choose Picture"), REQUEST_IMAGE_GET);
                }else{
                    Toast.makeText(getApplicationContext(), "No permission for files", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getIntExtra("id", -1);
            impacterType = ImpactType.valueOf(intent.getStringExtra(IMPACTERTYPE));

            impacter = db.getSelectedCO2Impacter(impacterType);
//            impacter = dh.getImpacterByid(impacterType, id);
            setData(impacter, impacterType);
            title.setText("Edit " + impacterType.name().toLowerCase());

        } else {
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
                Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
            //if the input is empty
            else {
                try {
//                setting data in impacter
                    impacter.setName(name.getText().toString());
                    impacter.setQuestion(Question.getText().toString());
                    ((Transportation) impacter).setFuelType(fuelType.getText().toString());
                    impacter.setCo2Amount(Integer.parseInt(co2Amount.getText().toString()));
                    impacter.setUnit(Units.valueOf(String.valueOf(spinner.getSelectedItem())));
//                    impacter.setImg(db.getSelectedCO2Impacter(impacterType).getImg());
//                    dh.addImpacter(impacterType, impacter);

                    //add to database
                    int id = db.updateCo2Impacter((Transportation) impacter);
                    db.updateTransportation(id, (Transportation) impacter);

                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    String toSpeak = "add successfully";
                    Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
//                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
//                    finish();
                } catch (Throwable ew) {
                    ew.printStackTrace();
                }
            }
        } else if ((impacterType.equals(ImpactType.ELECTRICAL)) || (impacterType.equals(ImpactType.SERVICES)) || (impacterType.equals(ImpactType.FOOD))) {
            if ((TextUtils.isEmpty(name.getText().toString())) || (TextUtils.isEmpty(Question.getText().toString())) ||
                    (TextUtils.isEmpty(co2Amount.getText().toString()))) {
//                Toast.makeText(context,
//                        "add failed..There are empty input!",
//                        Toast.LENGTH_SHORT).show();
                String toSpeak = "add failed..There are empty input!";
                Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);


            }
            //if the input is empty
            else {
                try {
                    //                setting data in impacter
                    impacter.setName(name.getText().toString());
                    impacter.setQuestion(Question.getText().toString());
                    impacter.setCo2Amount(Integer.parseInt(co2Amount.getText().toString()));
                    impacter.setUnit(Units.valueOf(String.valueOf(spinner.getSelectedItem())));
//                    impacter.setImg(db.getSelectedCO2Impacter(impacterType).getImg());
//                    dh.addImpacter(impacterType, impacter);
                    db.updateCo2Impacter(impacter);

                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    String toSpeak = "add successfully";
                    Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                    finish();
                } catch (Throwable ew) {
                    ew.printStackTrace();
                }
            }
        }
    }

    // Function to find the index of an element
    public int findIndex(List<Units> arr, Units t) {
        int i = 0;
        for (Units a : arr) {
            if (a.equals(t)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    //set data info in view fields
    private void setData(Co2Impacter impacter, ImpactType type) {

        name.setText(impacter.getName());
        Question.setText(impacter.getQuestion());
        co2Amount.setText(String.valueOf(impacter.getCo2Amount()));
        List<Units> enumValues = Arrays.asList(Units.values());
        Log.d("get(0)", enumValues.get(0).toString());
        Log.d("find method", String.valueOf(findIndex(enumValues, impacter.getUnit())));
        spinner.setSelection(findIndex(enumValues, impacter.getUnit()));
        imageUpload.setImageDrawable(new BitmapDrawable(context.getResources(), impacter.getImg()));

        if (impacter instanceof Transportation) {
            fuelType.setText(((Transportation) impacter).getFuelType());
        } else {
            fuelType.setVisibility(View.GONE);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //get picture from phone gallery
        if (requestCode == REQUEST_IMAGE_GET && resultCode == Activity.RESULT_OK) {
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        Units item = (Units) parent.getItemAtPosition(position);
        impacter.setUnit(item);
        // Showing selected spinner item
//        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
//        Units item = (Units) parent.getItemAtPosition(0);
//        impacter.setUnit(item);
//        Toast.makeText(parent.getContext(), "Please select units " , Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        MyCo2FootprintManager.getInstance().openDataBase(this);
        super.onResume();

    }

    @Override
    protected void onPause() {
        Log.d("Edit",",aa");
        MyCo2FootprintManager.getInstance().closeDataBase();
        super.onPause();
    }
}