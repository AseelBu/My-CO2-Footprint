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

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddingItemActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String TAG = "AddingItemActivity";
    public static final int REQUEST_IMAGE_GET = 3;
    protected static final int NEW_ITEM_TAG = -111;
    private static final String IMPACTERTYPE = "ImpacterType";
    private static final String IMPACTER_COLLECTION = "co2 impacter";
    private static final int ADDING_REQ_CODE = 100;
    private final MyCo2FootprintManager db = MyCo2FootprintManager.getInstance();
    public EditText name = null;
    public EditText fuelType = null;
    public EditText co2Amount = null;
    public EditText question = null;
    public Spinner spinner;
    public ImageButton img;
    Bitmap bitmap = null;
    Units unit;
    TextToSpeech t1;
    private ImpactType impacterType;
    private Co2Impacter impacter = null;
    private TextView title;
    private Context context;
    private FirebaseFirestore dbc = FirebaseFirestore.getInstance();
    private SharedPreferences prefs = null;
    private Button addBtn;
    private ImageButton uploadImgBtn;
    private TextView textFuel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MyCo2FootprintManager.getInstance().openDataBase(this);
        setContentView(R.layout.activity_adding_item);
        title = (TextView) findViewById(R.id.addingActivityTitle);
        img = (ImageButton) findViewById(R.id.upload);
        name = (EditText) findViewById(R.id.type);
        fuelType = (EditText) findViewById(R.id.fuel3);

        co2Amount = (EditText) findViewById(R.id.amountt);
        question = (EditText) findViewById(R.id.Question);
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
        textFuel = (TextView) findViewById(R.id.textFuelAdding);
        if (intent != null) {
            impacterType = ImpactType.valueOf(intent.getStringExtra(IMPACTERTYPE));
            title.setText("Add " + impacterType.name().toLowerCase());
            createImpacter();
            //remove fuel type field if not transportation
            if (!impacterType.equals(ImpactType.TRANSPORTATION)) {

                fuelType.setVisibility(View.GONE);
                textFuel.setVisibility(View.GONE);
            }

        } else {

            finish();
        }

        addBtn = (Button) findViewById(R.id.edititem2);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBtnClicked();
            }
        });

        uploadImgBtn = (ImageButton) findViewById(R.id.upload);
        uploadImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(Intent.createChooser(intent, "No permission for files"), REQUEST_IMAGE_GET);
                } else {
                    String toSpeak = "No permission for files!";
                    View parentLayout = findViewById(android.R.id.content);
                    final Snackbar bar = Snackbar.make(parentLayout, toSpeak, Snackbar.LENGTH_INDEFINITE);
                    bar.setAction("Dismiss", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bar.dismiss();
                        }
                    });
                    bar.setActionTextColor(getResources().getColor(R.color.dangerRed));
                    bar.show();


                }
            }
        });
        context = this;
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
                View parentLayout = findViewById(android.R.id.content);
                final Snackbar bar = Snackbar.make(parentLayout, toSpeak, Snackbar.LENGTH_INDEFINITE);
                bar.setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bar.dismiss();
                    }
                });
                bar.setActionTextColor(getResources().getColor(R.color.dangerRed));
                bar.show();
            }
            //if the input is not empty
            else {

                try {

                    //setting data in impacter
                    impacter.setName(name.getText().toString());
                    impacter.setQuestion(question.getText().toString());
                    ((Transportation) impacter).setFuelType(fuelType.getText().toString());
                    impacter.setCo2Amount(Integer.parseInt(co2Amount.getText().toString()));
                    impacter.setUnit(Units.valueOf(String.valueOf(spinner.getSelectedItem())));
                    FirebaseFirestore db2 = FirebaseFirestore.getInstance();
                    impacter.setImpacterID(UUID.randomUUID().toString());
                    if (impacter.getImg() != null) {
                        impacter.setUrlImage(impacter.getImpacterID() + ".png");
                    }
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", impacter.getName());
                    map.put("unit", impacter.getUnit().toString());
                    map.put("question", impacter.getQuestion());
                    map.put("fuelType", ((Transportation) impacter).getFuelType());
                    map.put("co2Amount", impacter.getCo2Amount());
                    map.put("impacterType", impacterType);
                    map.put("urlImage", null);
//                    map.put("urlImage",impacter.getUrlImage());
                    db2.collection(IMPACTER_COLLECTION)
                            .document(String.valueOf(impacter.getImpacterID()))
                            .set(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    db.openDataBase(AddingItemActivity.this);
                                    db.createCO2Impacter(impacter);
                                    db.createTransportation(impacter.getImpacterID(), (Transportation) impacter);
                                    String toSpeak = "add successfully";
                                    View parentLayout = findViewById(android.R.id.content);
                                    final Snackbar bar = Snackbar.make(parentLayout, toSpeak, Snackbar.LENGTH_LONG);
                                    bar.setAction("dismiss", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            bar.dismiss();


                                        }
                                    });
                                    bar.setActionTextColor(getResources().getColor(R.color.dangerRed));
                                    bar.show();
                                    if (impacter.getImg() != null) {
                                        uploadImage(impacter);
                                        newActivity();
                                    } else {
                                        newActivity();
                                    }
                                    db.closeDataBase();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            final Snackbar bar = Snackbar.make(findViewById(android.R.id.content), e.getMessage(), Snackbar.LENGTH_INDEFINITE);
                            bar.setAction("dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    bar.dismiss();

                                }
                            });
                            bar.setActionTextColor(getResources().getColor(R.color.dangerRed));
                            bar.show();
                        }
                    });


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
                String toSpeak = "add failed..There are empty input!";
                View parentLayout = findViewById(android.R.id.content);
                final Snackbar bar = Snackbar.make(parentLayout, toSpeak, Snackbar.LENGTH_INDEFINITE);
                bar.setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bar.dismiss();
                    }
                });
                bar.setActionTextColor(getResources().getColor(R.color.dangerRed));
                bar.show();
            }


            //if the input is empty
            else {
                try {
                    //                setting data in impacter
                    impacter.setName(name.getText().toString());
                    impacter.setQuestion(question.getText().toString());
                    impacter.setCo2Amount(Integer.parseInt(co2Amount.getText().toString()));
                    impacter.setUnit(Units.valueOf(String.valueOf(spinner.getSelectedItem())));
                    impacter.setImpacterID(UUID.randomUUID().toString());
                    if (impacter.getImg() != null) {
                        impacter.setUrlImage(impacter.getImpacterID() + ".png");
                    }


                    Map<String, Object> map = new HashMap<>();
                    map.put("name", impacter.getName());
                    map.put("unit", impacter.getUnit().toString());
                    map.put("question", impacter.getQuestion());
                    map.put("co2Amount", impacter.getCo2Amount());
                    map.put("impacterType", impacterType);
                    map.put("urlImage", null);
                    dbc.collection(IMPACTER_COLLECTION)
                            .document(String.valueOf(impacter.getImpacterID()))
                            .set(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    db.openDataBase(AddingItemActivity.this);
                                    db.createCO2Impacter(impacter);
                                    switch (impacterType) {
                                        case TRANSPORTATION:
                                            db.createTransportation(impacter.getImpacterID(), (Transportation) impacter);
                                            break;
                                        case SERVICES:
                                            db.createService(impacter.getImpacterID(), (Service) impacter);
                                            break;
                                        case FOOD:
                                            db.createFood(impacter.getImpacterID(), (Food) impacter);
                                            break;
                                        case ELECTRICAL:
                                            db.createElectric(impacter.getImpacterID(), (ElectricalHouseSupplies) impacter);
                                            break;
                                    }
                                    String toSpeak = "add successfully";
                                    View parentLayout = findViewById(android.R.id.content);
                                    final Snackbar bar = Snackbar.make(parentLayout, toSpeak, Snackbar.LENGTH_LONG);
                                    bar.setAction("dismiss", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            bar.dismiss();

                                        }
                                    });
                                    bar.setActionTextColor(getResources().getColor(R.color.dangerRed));
                                    bar.show();
                                    if (impacter.getImg() != null) {
                                        uploadImage(impacter);
                                        newActivity();
                                    } else {
                                        newActivity();
                                    }
                                    db.closeDataBase();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            final Snackbar bar = Snackbar.make(findViewById(android.R.id.content), e.getMessage(), Snackbar.LENGTH_INDEFINITE);
                            bar.setAction("dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    bar.dismiss();

                                }
                            });
                            bar.setActionTextColor(getResources().getColor(R.color.dangerRed));
                            bar.show();
                        }
                    });

                } catch (Throwable ew) {
                    ew.printStackTrace();
                }
            }


        }
    }

    public void newActivity() {
        Intent intent = new Intent(this, AdminEditListActivity.class);
        intent.putExtra(IMPACTERTYPE, impacterType.name());
        intent.putExtra("comebackName", impacter.getName());
        intent.putExtra("action", "added");
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, AdminEditListActivity.class);
                intent.putExtra(IMPACTERTYPE, impacterType.name());
                startActivity(intent);
                finish();
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

    private void uploadImage(Co2Impacter imp) {
        try {
            Bitmap bitmap = imp.getImg();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] data = baos.toByteArray();

            FirebaseStorage storage = FirebaseStorage.getInstance();
            // Create a storage reference from our app
            StorageReference storageRef = storage.getReference();
            final String imageName = imp.getImpacterID() + ".png";
            StorageReference imageRef = storageRef.child(imageName);

            UploadTask uploadTask = imageRef.putBytes(data);


            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                    final Snackbar bar = Snackbar.make(findViewById(android.R.id.content), exception.getMessage(), Snackbar.LENGTH_INDEFINITE);
                    bar.setAction("Dismiss", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bar.dismiss();
                        }
                    });
                    bar.setActionTextColor(getResources().getColor(R.color.dangerRed));
                    bar.show();

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    imp.setUrlImage(impacter.getUrlImage());
                    dbc.collection(IMPACTER_COLLECTION).document(imp.getImpacterID())
                            .update("urlImage", imp.getUrlImage()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            db.openDataBase(AddingItemActivity.this);
                            db.updateCo2Impacter(imp);
                            db.closeDataBase();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            final Snackbar bar = Snackbar.make(findViewById(android.R.id.content), e.getMessage(), Snackbar.LENGTH_INDEFINITE);
                            bar.setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    bar.dismiss();
                                }
                            });
                            bar.setActionTextColor(getResources().getColor(R.color.dangerRed));
                            bar.show();
                        }
                    });
                }
            });
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}