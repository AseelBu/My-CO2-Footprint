package com.androidcourse.energyconsumptiondiary_androidapp.Adapters;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.androidcourse.energyconsumptiondiary_androidapp.AdminEditListActivity;
import com.androidcourse.energyconsumptiondiary_androidapp.EditItemActivity;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Co2Impacter;

import com.androidcourse.energyconsumptiondiary_androidapp.Model.MyCo2FootprintManager;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Transportation;
import com.androidcourse.energyconsumptiondiary_androidapp.R;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;
import com.androidcourse.energyconsumptiondiary_androidapp.core.ImpactType;

import java.util.ArrayList;
import java.util.List;

public class AdminEditRecyclerViewAdapter extends RecyclerView.Adapter<AdminEditRecyclerViewAdapter.ViewHolder>{

    private static  final int ADDING_REQ_CODE =100;
    private static  final int EDIT_REQ_CODE =101;
    private static final String IMPACTERTYPE = "ImpacterType";

    private final DataHolder dh = DataHolder.getInstance();
    private Context context;
    private ImpactType impacterType;
    private ArrayList<? extends Co2Impacter> data=new ArrayList<>();

    public AdminEditRecyclerViewAdapter(Context context, ImpactType impacterType) {
        this.context = context;
        this.impacterType=impacterType;
        this.data=dh.getImpactersByType(impacterType);
    }

    @NonNull
    @Override
    public AdminEditRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.admin_view_list_element, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminEditRecyclerViewAdapter.ViewHolder holder, int position) {
        Co2Impacter impacter = data.get(position);
        holder.setData(impacter);
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(holder.getAdapterPosition(),holder);
            }
        });
        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageButton deleteBtn;
        private ImageView impacterImage;
        private TextView impacterName;
        private TextView fuelType;
        private TextView impacterCo2;
        private ImageButton editBtn;

        private Co2Impacter impacter=null;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deleteBtn=(ImageButton) itemView.findViewById(R.id.deleteAdminRow);

            impacterImage=(ImageView)itemView.findViewById(R.id.imImageAdminList);
            impacterName=(TextView)itemView.findViewById(R.id.imNameAdminList);
            fuelType=(TextView)itemView.findViewById(R.id.imfuelTypeAdminList);
            impacterCo2=(TextView)itemView.findViewById(R.id.imCo2AdminList);
            editBtn=(ImageButton) itemView.findViewById(R.id.editAdminRow);


        }

        public void setData(Co2Impacter impacter){
            this.impacter=impacter;
            impacterName.setText(impacter.getName());
            impacterCo2.setText(String.valueOf(impacter.getCo2Amount()));
//            if(impacter.getImg()!=null) {
                impacterImage.setImageDrawable(new BitmapDrawable(context.getResources(), impacter.getImg()));
//            }else{
//                impacterImage.setVisibility(View.GONE);
//            }

            if(impacterType.equals(ImpactType.TRANSPORTATIOIN)) {

                fuelType.setText(((Transportation)impacter).getFuelType());
            }else{
                fuelType.setVisibility(View.GONE);
            }

        }
    }

    public void updateImpactersData(){

        this.data=dh.getImpactersByType(impacterType);
    }

    //deletes impacter from data holder and from view
    public void delete(int position, ViewHolder holder) {
        new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_delete)
                .setTitle("Are you sure ?")
                .setMessage("Do you want to delete "+data.get(position).getName())
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {



                        try {
                            List<? extends Co2Impacter> impacters = null;
                            Co2Impacter impacterItem = impacters.get(holder.getAdapterPosition());
                            if (impacterItem instanceof Transportation) {
                                Co2Impacter item = MyCo2FootprintManager.getInstance().getSelectedTransporatation();
                                if (item != null) {
                                    MyCo2FootprintManager.getInstance().deleteTransportation((Transportation) item);
                                }
                                dh.removeImpacter(impacterType, data.get(position));
                                updateImpactersData();
                                notifyDataSetChanged();


                            }
                        }catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    //edit item from the list
        public void edit(int position) {
            Intent intent = new Intent(context, EditItemActivity.class);
            intent.putExtra("id",data.get(position).getImpacterID());
            intent.putExtra(IMPACTERTYPE,impacterType.name());
            ((AdminEditListActivity)context).startActivityForResult(intent,EDIT_REQ_CODE);
        }
}
