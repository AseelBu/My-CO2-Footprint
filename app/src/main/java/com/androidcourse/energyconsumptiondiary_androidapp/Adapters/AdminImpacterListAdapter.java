package com.androidcourse.energyconsumptiondiary_androidapp.Adapters;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import com.androidcourse.energyconsumptiondiary_androidapp.AdminEditListActivity;
import com.androidcourse.energyconsumptiondiary_androidapp.EditItemActivity;
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
import java.util.List;

public class AdminImpacterListAdapter extends ArrayAdapter<Co2Impacter> {
    private static final String IMPACTERTYPE = "ImpacterType";
    public boolean flag = false;
    private List<Co2Impacter> dataList = null;
    private Context context = null;
    private ImpactType impacterType;
    private MyCo2FootprintManager db = MyCo2FootprintManager.getInstance();

    public AdminImpacterListAdapter(@NonNull Context context, List<Co2Impacter> dataList, ImpactType impacterType) {
        super(context, R.layout.admin_view_list_element, dataList);
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
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView = inflater.inflate(R.layout.admin_view_list_element, null, false);
        ImageButton deleteBtn = (ImageButton) rowView.findViewById(R.id.deleteAdminRow);
        ImageView impacterImage = (ImageView) rowView.findViewById(R.id.imImageAdminList);
        TextView impacterName = (TextView) rowView.findViewById(R.id.imNameAdminList);
        TextView fuelType = (TextView) rowView.findViewById(R.id.imfuelTypeAdminList);
        TextView impacterCo2 = (TextView) rowView.findViewById(R.id.imCo2AdminList);
        ImageButton editBtn = (ImageButton) rowView.findViewById(R.id.editAdminRow);
        final Co2Impacter impacter = dataList.get(position);
        impacterName.setText(impacter.getName());
        impacterCo2.setText(String.valueOf(impacter.getCo2Amount()));
        if (impacter.getImg() != null) {
            impacterImage.setImageDrawable(new BitmapDrawable(context.getResources(), impacter.getImg()));
        }
        if (impacterType.equals(ImpactType.TRANSPORTATION)) {

            fuelType.setText(((Transportation) impacter).getFuelType());
        } else {
            fuelType.setVisibility(View.GONE);
        }

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
                db.openDataBase(context);
                db.setSelectedCO2Impacter(impacter);
                delete(impacter);
                db.closeDataBase();
            }
        });

        //set images
        final ImageView imageView = rowView.findViewById(R.id.imImageAdminList);
        String imageUrl = impacter.getUrlImage();

        if (imageUrl != null) {
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
                                        .into(imageView);
                            } else {
                                System.out.println("Getting download url was not successful." +
                                        task.getException());
                            }
                        }
                    });
        }
        return rowView;
    }


    //deletes impacter from data holder and from view
    public boolean delete(Co2Impacter imp) {

        new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_delete)
                .setTitle("Are you sure ?")
                .setMessage("Do you want to delete " + imp.getName())
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            flag = true;
                            db.openDataBase(context);
                            List<? extends Co2Impacter> impacters = db.getAllCo2Impacter();
                            if (imp != null) {
                                FirebaseFirestore dbc = FirebaseFirestore.getInstance();
                                dbc.collection("co2 impacter")
                                        .document(imp.getImpacterID())
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                MyCo2FootprintManager.getInstance().removeImpacter(impacterType, imp.getImpacterID());
                                                String imageUrl = imp.getUrlImage();

                                                if (imageUrl != null) {
                                                    deleteImageFromCloudStorage(imageUrl);
                                                }
                                                db.removeImpacter(impacterType, imp.getImpacterID());
                                                Toast.makeText(context, imp.getName()+" deleted", Toast.LENGTH_LONG).show();

                                            }
                                        })

                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                                            }

                                        });
                            }

                        } catch (Throwable e) {
                            e.printStackTrace();
                        } finally {
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
        ((AdminEditListActivity) context).startActivity(intent);
        ((AdminEditListActivity) context).finish();
    }

    public void updateImpactersData() {
        db.openDataBase(context);
        this.dataList = db.getImpactersByType(impacterType);
        db.closeDataBase();
    }

    private void deleteImageFromCloudStorage(String imageUrl) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference storageReference = storageRef.child(imageUrl);
        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("impacter image deleted successfully!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("impacter image deletion failed!");
            }
        });
    }

}
