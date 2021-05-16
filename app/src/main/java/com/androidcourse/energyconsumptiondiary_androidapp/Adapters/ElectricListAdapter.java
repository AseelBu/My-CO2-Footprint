//package com.androidcourse.energyconsumptiondiary_androidapp.Adapters;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//import androidx.appcompat.app.AlertDialog;
//import com.androidcourse.energyconsumptiondiary_androidapp.EditItemActivitya;
//import com.androidcourse.energyconsumptiondiary_androidapp.ElectricMain;
//import com.androidcourse.energyconsumptiondiary_androidapp.Model.ElectricalHouseSupplies;
//import com.androidcourse.energyconsumptiondiary_androidapp.R;
//import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;
//import java.util.List;
//
//public class ElectricListAdapter extends ArrayAdapter<ElectricalHouseSupplies2> {
//    private static final int REQ_CODE = 123;
//    private DataHolder dh = DataHolder.getInstance();
//    private List<ElectricalHouseSupplies2> dataList = null;
//    private Context context = null;
//
//    //ElectricListAdapter Constructo
//    public ElectricListAdapter(Context context, List<ElectricalHouseSupplies2> dataList) {
//        super(context, R.layout.electric_list, dataList);
//        this.dataList = dataList;
//        this.dataList.add(0,new ElectricalHouseSupplies2());
//        this.context = context;
//    }
//
//    //get datalist size
//    @Override
//    public int getCount() {
//        return dataList.size();
//    }
//    //return list from ElectricalHouseSupplies2 type
//    public List<ElectricalHouseSupplies2> getList() {
//        return this.dataList ;
//    }
//
//    //add item to list
//    public void addToList(ElectricalHouseSupplies2 e) {
//        dataList.add(e);
//    }
//
//    //remove item to list
//    public void removeFromList(int e) {
//        dataList.remove(e);
//    }
//
//    //get item from list
//    @Override
//    public ElectricalHouseSupplies2 getItem(int position) {
//        return dataList.get(position);
//    }
//
//    //get view from list
//    @Override
//    public View getView(int position, View view, ViewGroup parent) {
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View rowView;
//        if (position == 0) {
//            rowView = inflater.inflate(R.layout.listheader_electric, null, false);
//            ImageView add = (ImageView) rowView.findViewById(R.id.adding);
//        } else {
//            rowView=inflater.inflate(R.layout.electric_list, null, false);
//            TextView txtTitle3 = (TextView) rowView.findViewById(R.id.Co2Amounta);
//            ImageView edit = (ImageView) rowView.findViewById(R.id.edita);
//            ImageView delete = (ImageView) rowView.findViewById(R.id.delete2a);
//            ImageView imageView = (ImageView) rowView.findViewById(R.id.electric2);
//            final ElectricalHouseSupplies2 itemInfo = dataList.get(position);
//            txtTitle3.setText(String.valueOf(itemInfo.getCO2Amount()));
//            imageView.setImageDrawable(itemInfo.getImg());
//            delete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    delete2(position);
//                }
//            });
//
//            edit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    edit2(position);
//                }
//            });
//        }
//        return rowView;
//    }
//
//     //delete item from the list
//     public void delete2(int position) {
//         //show AlertDialog
//         new AlertDialog.Builder(context)
//                .setIcon(android.R.drawable.ic_delete)
//                .setTitle("Are you sure ?")
//                .setMessage("Do you want to delete this item")
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dataList.remove(position);
//                        notifyDataSetChanged();
//                    }
//                })
//                .setNegativeButton("No",null)
//                .show();
//    }
//
//    //edit item in the list method
//    public void edit2(int position) {
//        Intent intent = new Intent(context, EditItemActivitya.class);
//        intent.putExtra("positiona",position);
//        intent.putExtra("Typea",dataList.get(position).getName());
//        intent.putExtra("CO2Amounta",(String.valueOf(dataList.get(position).getCO2Amount())));
//        ((ElectricMain)context).startActivityForResult(intent,REQ_CODE);
//    }
//}
