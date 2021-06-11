package com.androidcourse.energyconsumptiondiary_androidapp.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.androidcourse.energyconsumptiondiary_androidapp.AdminEditListActivity;
import com.androidcourse.energyconsumptiondiary_androidapp.EditItemActivity;
import com.androidcourse.energyconsumptiondiary_androidapp.ItemInfo;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Co2Impacter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.MyCo2FootprintManager;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Transportation;
import com.androidcourse.energyconsumptiondiary_androidapp.R;
import com.androidcourse.energyconsumptiondiary_androidapp.core.ImpactType;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.List;


public class AdminImpacterListAdapter extends ArrayAdapter<Co2Impacter> {
    private List<Co2Impacter> dataList = null;
    private Context context = null;
    private ImpactType impacterType;
    public boolean flag=false;
    public Co2Impacter imp;
    private static final String IMPACTERTYPE = "ImpacterType";

    private MyCo2FootprintManager db = MyCo2FootprintManager.getInstance();


    public AdminImpacterListAdapter(@NonNull Context context, List<Co2Impacter> dataList, ImpactType impacterType) {
        super(context, R.layout.admin_view_list_element,dataList);
        this.context = context;
        this.impacterType = impacterType;
        this.dataList = dataList;
    }


    //get number of dataList size
    @Override
    public int getCount() {
        return dataList.size();
    }
    //get item in list
    @Override
    public Co2Impacter getItem(int position) {
        return dataList.get(position);
    }

    //get view in list
    @Override
    public View getView(int position, @Nullable View view,@NonNull ViewGroup parent) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View rowView =null;
        rowView = inflater.inflate(R.layout.admin_view_list_element, null, false);
        final Co2Impacter imp= dataList.get(position);
        ImageButton deleteBtn = (ImageButton) rowView.findViewById(R.id.deleteAdminRow);
        ImageView impacterImage = (ImageView) rowView.findViewById(R.id.imImageAdminList);
        TextView impacterName = (TextView) rowView.findViewById(R.id.imNameAdminList);
        TextView fuelType = (TextView) rowView.findViewById(R.id.imfuelTypeAdminList);
        TextView impacterCo2 = (TextView) rowView.findViewById(R.id.imCo2AdminList);
        ImageButton editBtn = (ImageButton) rowView.findViewById(R.id.editAdminRow);
        final Co2Impacter impacter = dataList.get(position);
        impacterName.setText(impacter.getName());
        impacterCo2.setText(String.valueOf(impacter.getCo2Amount()));
        if(impacter.getImg()!=null) {
            impacterImage.setImageDrawable(new BitmapDrawable(context.getResources(), impacter.getImg()));
        }
        if (impacterType.equals(ImpactType.TRANSPORTATION)) {

            fuelType.setText(((Transportation) impacter).getFuelType());
        } else {
            fuelType.setVisibility(View.GONE);
        }

        //adding buttons listeners
//        deleteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                db.openDataBase(context);
//                db.setSelectedCO2Impacter(impacter);
//                delete(impacter);
//                db.closeDataBase();
//            }
//        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.openDataBase(context);
                db.setSelectedCO2Impacter(impacter);
                edit(impacter);
                db.closeDataBase();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                db.openDataBase(context);
//                db.setSelectedCO2Impacter(impacter);
//                //delete(impacter);
//                db.closeDataBase();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("co2 impacter")
                        .document(String.valueOf(imp.getImpacterID()))
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                MyCo2FootprintManager.getInstance().removeImpacter(impacterType,imp.getImpacterID());
                                String imageUrl = String.valueOf(imp.getImg());
                                Log.d("aaaaaaaaaaaa",imageUrl);
                                if(imageUrl!=null) {
                                    if(delete(impacter)==true)
                                    deleteImageFromCloudStorage(imageUrl);
                                }
//                                if(dataList.size()>0) {
//                                    dataList.remove(position);
//                                    AdminImpacterListAdapter.this.notifyDataSetChanged();
//                                }

                            }
                        })

                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                            }

                        });

            }

        });
        final ImageView imageView = rowView.findViewById(R.id.imImageAdminList);
        String imageUrl = imp.getUrlImage();

        if(imageUrl!=null){
//            ByteArrayOutputStream boas=new ByteArrayOutputStream();
//            imageUrl.compress(Bitmap.CompressFormat.PNG,100,boas);
//            byte[] data=boas.toByteArray();
            FirebaseStorage  storage= FirebaseStorage.getInstance();
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
                                        .into(imageView);
                            } else {
                                System.out.println( "Getting download url was not successful."+
                                        task.getException());
                            }
                        }
                    });
        }
        return rowView;
    }




    //deletes impacter from data holder and from view
    public boolean delete(Co2Impacter impacter) {


        new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_delete)
                .setTitle("Are you sure ?")
                .setMessage("Do you want to delete " + impacter.getName())
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            flag=true;
                            db.openDataBase(context);
                            List<? extends Co2Impacter> impacters = MyCo2FootprintManager.getInstance().getAllCo2Impacter();
                            Co2Impacter item = db.getSelectedCO2Impacter(impacterType);
                            if (item != null) {

                                db.removeImpacter(impacterType, impacter.getImpacterID());
                            }
                            updateImpactersData();
                            notifyDataSetChanged();
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }finally {
                            db.closeDataBase();
                        }

                    }
                })
                .setNegativeButton("No", null)
                .show();
        return flag;
    }

    //edit item from the list
    public void edit(Co2Impacter impacter) {
        Intent intent = new Intent(context, EditItemActivity.class);
        intent.putExtra("id", impacter.getImpacterID());
        intent.putExtra(IMPACTERTYPE, impacterType.name());
        ((AdminEditListActivity)context).startActivity(intent);
        ((AdminEditListActivity)context).finish();
    }


    public void updateImpactersData() {
        db.openDataBase(context);
        this.dataList = db.getImpactersByType(impacterType);
        db.closeDataBase();
    }

    private void deleteImageFromCloudStorage(String imageUrl) {
        FirebaseStorage storage= FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference storageReference = storageRef.child(imageUrl);
        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println( "oK!");
            }
        });
    }

//    public void uploadImage() {
//        try {
//            imp=MyCo2FootprintManager.getInstance().getSelectedCO2Impacter(impacterType);
//            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
//                    android.R.drawable.ic_menu_call);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//            byte[] data = baos.toByteArray();
//
//            FirebaseStorage storage = FirebaseStorage.getInstance();
//            // Create a storage reference from our app
//            StorageReference storageRef = storage.getReference();
//            final String imageName = imp.getImpacterID() + ".PNG";
//            StorageReference imageRef = storageRef.child(imageName);
//
//            UploadTask uploadTask = imageRef.putBytes(data);
//
//
//            uploadTask.addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//                    // Handle unsuccessful uploads
//                    System.out.println(exception);
//                }
//            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    String base64String = imageName;
//                    String base64Image = base64String.split(",")[1];
//
//                    byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
//                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//
//                    imp.setImg(decodedByte);
//                    MyCo2FootprintManager.getInstance().updateCo2Impacter(imp);
//                }
//            });
//        }catch (Throwable t){
//            t.printStackTrace();
//        }
//    }
}
