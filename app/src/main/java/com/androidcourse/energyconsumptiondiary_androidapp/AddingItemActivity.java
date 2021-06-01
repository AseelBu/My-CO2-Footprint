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
import com.androidcourse.energyconsumptiondiary_androidapp.Model.ElectricalHouseSupplies;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Food;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.MyCo2FootprintManager;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Service;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Transportation;
import com.androidcourse.energyconsumptiondiary_androidapp.core.ImpactType;
import com.androidcourse.energyconsumptiondiary_androidapp.core.Units;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AddingItemActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String TAG = "AddingItemActivity";
    private static final String IMPACTERTYPE = "ImpacterType";
    public static final int REQUEST_IMAGE_GET = 3;
    private ImpactType impacterType;
    private Co2Impacter impacter=null;
    protected static final int NEW_ITEM_TAG = -111;
    private TextView title;
    public EditText name = null;
    public EditText fuelType = null;
    public EditText co2Amount= null;
    public EditText question = null;
    public  Spinner spinner;
    public ImageButton img;
    Bitmap bitmap = null;
    Units unit;
    private Context context;
    private final MyCo2FootprintManager db = MyCo2FootprintManager.getInstance();
    private SharedPreferences prefs = null;
    private Button addBtn;
    private ImageButton uploadImgBtn;
    TextToSpeech t1;

    private TextView textFuel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_item);
        title=(TextView)findViewById(R.id.addingActivityTitle);
        img=(ImageButton)findViewById(R.id.upload);
        name=(EditText)findViewById(R.id.type);
        fuelType = (EditText) findViewById(R.id.fuel3);

        co2Amount=(EditText)findViewById(R.id.amountt);
        question =(EditText)findViewById(R.id.Question);
        spinner = (Spinner) findViewById(R.id.spinner);
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
        Intent intent = getIntent();
        textFuel=(TextView)findViewById(R.id.textFuelAdding);
        if (intent != null) {
            impacterType = ImpactType.valueOf(intent.getStringExtra(IMPACTERTYPE));
            title.setText("Add " + impacterType.name().toLowerCase());
            createImpacter();
            //remove fuel type field if not transportation
            if(!impacterType.equals(ImpactType.TRANSPORTATION)){

                fuelType.setVisibility(View.GONE);
                textFuel.setVisibility(View.GONE);
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
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(Intent.createChooser(intent,"Choose Picture"), REQUEST_IMAGE_GET);
                }else{
                    Toast.makeText(getApplicationContext(), "No permission for files", Toast.LENGTH_SHORT).show();
                }
            }
        });
        context=this;
        //action bar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
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
        switch (impacterType) {
            case TRANSPORTATION:
                impacter = new Transportation();
                break;
            case FOOD:
                impacter = new Food();
                break;
            case ELECTRICAL:
                impacter = new ElectricalHouseSupplies();
                break;
            case SERVICES:
                impacter = new Service();
                break;
        }
    }

    //add item to the data holder
    public void addBtnClicked() {
        if (impacterType.equals(ImpactType.TRANSPORTATION)) {


            //if text field is empty
            if ((TextUtils.isEmpty(name.getText().toString()))
                    || (TextUtils.isEmpty(question.getText().toString()))
                    || (TextUtils.isEmpty(co2Amount.getText().toString()))
                    || (TextUtils.isEmpty(fuelType.getText().toString()))) {
                String toSpeak = "add failed..There are empty input!";
                Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
//                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
            //if the input is not empty
            else {

                try {
                    //setting data in impacter
                    impacter.setName(name.getText().toString());
                    impacter.setQuestion(question.getText().toString());
//                    impacter.setImg(bitmap);
                    ((Transportation) impacter).setFuelType(fuelType.getText().toString());
                    impacter.setCo2Amount(Integer.parseInt(co2Amount.getText().toString()));
                    impacter.setUnit(Units.valueOf(String.valueOf(spinner.getSelectedItem())));
                    //save impacter to DB
                    int id = db.createCO2Impacter(impacter);
                    db.createTransportation(id, (Transportation) impacter);
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    String toSpeak = "add successfully";
                    Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
//                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                    finish();

                } catch (Throwable ew) {
                    ew.printStackTrace();
                }
            }
        }
        //if not transportation
        else if ((impacterType.equals(ImpactType.ELECTRICAL)) || (impacterType.equals(ImpactType.SERVICES)) || (impacterType.equals(ImpactType.FOOD))) {
            // if inputs are empty
            if ((TextUtils.isEmpty(name.getText().toString()))
                    || (TextUtils.isEmpty(question.getText().toString()))
                    || (TextUtils.isEmpty(co2Amount.getText().toString()))) {
                Toast.makeText(context,
                        "add failed..There are empty input!",
                        Toast.LENGTH_SHORT).show();
                String toSpeak = "add failed..There are empty input!";
                Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
//                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }


            //if the input is empty
            else {
                try {
                    //                setting data in impacter
                    impacter.setName(name.getText().toString());
                    impacter.setQuestion(question.getText().toString());
                    impacter.setCo2Amount(Integer.parseInt(co2Amount.getText().toString()));
                    impacter.setUnit(Units.valueOf(String.valueOf(spinner.getSelectedItem())));

//                    dh.addImpacter(impacterType, impacter);

                    int id = db.createCO2Impacter(impacter);
                    switch (impacterType)
                    {
                        case TRANSPORTATION:
                            db.createTransportation(id, (Transportation) impacter);
                            break;
                        case SERVICES:
                            db.createService(id, (Service) impacter);
                            break;
                        case FOOD:
                            db.createFood(id, (Food) impacter);
                            break;
                        case ELECTRICAL:
                            db.createElectric(id, (ElectricalHouseSupplies)impacter);
                            break;
                    }

                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    String toSpeak = "added successfully";
                    Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
//                            t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                    finish();
                } catch (Throwable ew) {
                    ew.printStackTrace();
                }
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
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onResume() {
        db.openDataBase(this);
        super.onResume();

    }

    @Override
    protected void onPause() {
        db.closeDataBase();
        super.onPause();
    }
}