package com.androidcourse.energyconsumptiondiary_androidapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Co2Impacter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.MyCo2FootprintManager;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Transportation;
import com.androidcourse.energyconsumptiondiary_androidapp.core.ImpactType;
import com.androidcourse.energyconsumptiondiary_androidapp.core.Units;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class EditItemActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String TAG = "EditItemActivity";
    public static final int REQUEST_IMAGE_GET = 3;
    private static  final int EDIT_REQ_CODE =101;
    private static final String IMPACTERTYPE = "ImpacterType";
    private static final String IMPACTER_COLLECTION = "co2 impacter";
    private final MyCo2FootprintManager db = MyCo2FootprintManager.getInstance();
    private FirebaseFirestore dbc = FirebaseFirestore.getInstance();
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
    private String id;
    private ImpactType impacterType;
    private Co2Impacter impacter;
    private TextView textFuel;
    private boolean isImgSet=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        MyCo2FootprintManager.getInstance().openDataBase(this);
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
                    startActivityForResult(Intent.createChooser(intent, "Choose Picture"), REQUEST_IMAGE_GET);
                } else {

                    String toSpeak = "No permission for files";
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
        Intent intent = getIntent();

        textFuel = (TextView) findViewById(R.id.textFuelEdit);

        if (intent != null) {
            id = intent.getStringExtra("id");
            impacterType = ImpactType.valueOf(intent.getStringExtra(IMPACTERTYPE));
            impacter = db.getSelectedCO2Impacter(impacterType);
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

    //add item to the data holder
    public void editClicked() {
        if (impacterType.equals(ImpactType.TRANSPORTATION)) {
            //if text field is empty
            if ((TextUtils.isEmpty(name.getText().toString()))
                    || (TextUtils.isEmpty(Question.getText().toString()))
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

                new AlertDialog.Builder(context)
                        .setIcon(R.drawable.ic_baseline_warning_24)
                        .setTitle("Are you sure ?")
                        .setMessage("Are you sure you want to save changes to " + impacter.getName())
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    //setting data in impacter
                                    impacter.setName(name.getText().toString());
                                    impacter.setQuestion(Question.getText().toString());
                                    ((Transportation) impacter).setFuelType(fuelType.getText().toString());
                                    impacter.setCo2Amount(Integer.parseInt(co2Amount.getText().toString()));
                                    impacter.setUnit(Units.valueOf(String.valueOf(spinner.getSelectedItem())));


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
                                    if(isImgSet){
                                        map.put("urlImage", impacter.getUrlImage());
                                    }else {
                                        map.put("urlImage", null);
                                    }

                                    dbc.collection(IMPACTER_COLLECTION)
                                            .document(String.valueOf(impacter.getImpacterID()))
                                            .set(map)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    db.openDataBase(EditItemActivity.this);
                                                    db.updateCo2Impacter(impacter);
                                                    db.updateTransportation(impacter.getImpacterID(), (Transportation) impacter);

                                                    View parentLayout = findViewById(android.R.id.content);
                                                    final Snackbar bar = Snackbar.make(parentLayout, impacter.getName()+" updated successfully", Snackbar.LENGTH_LONG);
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
                                } catch (Throwable e) {
                                    e.printStackTrace();
                                } finally {
                                    db.closeDataBase();
                                }
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        }

        //if not transportation
        else if ((impacterType.equals(ImpactType.ELECTRICAL)) || (impacterType.equals(ImpactType.SERVICES)) || (impacterType.equals(ImpactType.FOOD))) {
            // if inputs are empty
            if ((TextUtils.isEmpty(name.getText().toString()))
                    || (TextUtils.isEmpty(Question.getText().toString()))
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
            } else {

                new AlertDialog.Builder(context)
                        .setIcon(R.drawable.ic_baseline_warning_24)
                        .setTitle("Are you sure ?")
                        .setMessage("Are you sure you want to save changes to " + impacter.getName())
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    //setting data in impacter
                                    impacter.setName(name.getText().toString());
                                    impacter.setQuestion(Question.getText().toString());
                                    impacter.setCo2Amount(Integer.parseInt(co2Amount.getText().toString()));
                                    impacter.setUnit(Units.valueOf(String.valueOf(spinner.getSelectedItem())));

                                    if (impacter.getImg() != null) {
                                        impacter.setUrlImage(impacter.getImpacterID() + ".png");
                                    }
                                    Map<String, Object> map = new HashMap<>();
                                    map.put("name", impacter.getName());
                                    map.put("unit", impacter.getUnit().toString());
                                    map.put("question", impacter.getQuestion());
                                    map.put("co2Amount", impacter.getCo2Amount());
                                    map.put("impacterType", impacterType);
                                    if(isImgSet){
                                        map.put("urlImage", impacter.getUrlImage());
                                    }else {
                                        map.put("urlImage", null);
                                    }
                                    dbc.collection(IMPACTER_COLLECTION)
                                            .document(String.valueOf(impacter.getImpacterID()))
                                            .set(map)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    db.openDataBase(EditItemActivity.this);
                                                    db.updateCo2Impacter(impacter);
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
                                } catch (Throwable e) {
                                    e.printStackTrace();
                                } finally {
                                    db.closeDataBase();
                                }
                            }


                        })
                        .setNegativeButton("No", null)
                        .show();
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
        String imageUrl = impacter.getUrlImage();
        if (imageUrl != null) {
            isImgSet=true;
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            StorageReference storageReference = storageRef.child(imageUrl);
            storageReference.getDownloadUrl().addOnCompleteListener(
                    new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                String downloadUrl = task.getResult().toString();
                                Glide.with(context)
                                        .load(downloadUrl)
                                        .into(imageUpload);
                            } else {
                                System.out.println("Getting download url was not successful." +
                                        task.getException());
                            }
                        }
                    });
        }
        if (impacter instanceof Transportation) {
            fuelType.setText(((Transportation) impacter).getFuelType());
        } else {
            fuelType.setVisibility(View.GONE);
            textFuel.setVisibility(View.GONE);
        }
    }

    public void newActivity() {
        Intent intent = new Intent(context, AdminEditListActivity.class);
        intent.putExtra(IMPACTERTYPE, impacterType.name());
        intent.putExtra("comebackName", impacter.getName());
        intent.putExtra("action", "edited");
        startActivity(intent);
        finish();
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
                impacter = null;
                db.setSelectedCO2Impacter(null);
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
        MyCo2FootprintManager.getInstance().openDataBase(this);
        super.onResume();

    }

    @Override
    protected void onPause() {
        Log.d("Edit", ",aa");
        MyCo2FootprintManager.getInstance().closeDataBase();
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
                    if(!isImgSet) {
                        dbc.collection(IMPACTER_COLLECTION).document(imp.getImpacterID())
                                .update("urlImage", imp.getUrlImage()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                db.openDataBase(EditItemActivity.this);
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
                    }else{
                        db.openDataBase(EditItemActivity.this);
                        db.updateCo2Impacter(imp);
                        db.closeDataBase();
                    }

                }
            });
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}