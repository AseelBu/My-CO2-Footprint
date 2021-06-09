package com.androidcourse.energyconsumptiondiary_androidapp.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.androidcourse.energyconsumptiondiary_androidapp.AdminEditListActivity;
import com.androidcourse.energyconsumptiondiary_androidapp.EditItemActivity;
import com.androidcourse.energyconsumptiondiary_androidapp.ItemInfo;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Co2Impacter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.MyCo2FootprintManager;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Transportation;
import com.androidcourse.energyconsumptiondiary_androidapp.R;
import com.androidcourse.energyconsumptiondiary_androidapp.core.ImpactType;

import java.util.List;


public class AdminImpacterListAdapter extends ArrayAdapter<Co2Impacter> {
    private List<Co2Impacter> dataList = null;
    private Context context = null;
    private ImpactType impacterType;

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
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View rowView =null;
        rowView = inflater.inflate(R.layout.admin_view_list_element, null, false);

        ImageButton deleteBtn = (ImageButton) rowView.findViewById(R.id.deleteAdminRow);

        ImageView impacterImage = (ImageView) rowView.findViewById(R.id.imImageAdminList);
        TextView impacterName = (TextView) rowView.findViewById(R.id.imNameAdminList);
        TextView fuelType = (TextView) rowView.findViewById(R.id.imfuelTypeAdminList);
        TextView impacterCo2 = (TextView) rowView.findViewById(R.id.imCo2AdminList);
        ImageButton editBtn = (ImageButton) rowView.findViewById(R.id.editAdminRow);
        final Co2Impacter impacter = dataList.get(position);

        //set data
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
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.openDataBase(context);
                db.setSelectedCO2Impacter(impacter);
                delete(impacter);
                db.closeDataBase();
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.openDataBase(context);
                db.setSelectedCO2Impacter(impacter);
                edit(impacter);
                db.closeDataBase();
            }
        });

        return rowView;
    };

    //deletes impacter from data holder and from view
    public void delete(Co2Impacter impacter) {
        new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_delete)
                .setTitle("Are you sure ?")
                .setMessage("Do you want to delete " + impacter.getName())
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
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
    }

    //edit item from the list
    public void edit(Co2Impacter impacter) {
        Intent intent = new Intent(context, EditItemActivity.class);
        intent.putExtra("id", impacter.getImpacterID());
        intent.putExtra(IMPACTERTYPE, impacterType.name());
//        ((AdminEditListActivity) context).startActivityForResult(intent, EDIT_REQ_CODE);
        ((AdminEditListActivity)context).startActivity(intent);
        ((AdminEditListActivity)context).finish();
    }


    public void updateImpactersData() {
        db.openDataBase(context);
        this.dataList = db.getImpactersByType(impacterType);
        db.closeDataBase();
    }
}
