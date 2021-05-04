package com.androidcourse.energyconsumptiondiary_androidapp.Adapters;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import com.androidcourse.energyconsumptiondiary_androidapp.EditItemActivityb;
import com.androidcourse.energyconsumptiondiary_androidapp.FoodMain;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Food;
import com.androidcourse.energyconsumptiondiary_androidapp.R;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;
import java.util.List;

public class FoodListAdapter extends ArrayAdapter<Food> {
    private static final int REQ_CODE = 123;
    private DataHolder dh = DataHolder.getInstance();
    private List<Food> dataList = null;
    private Context context = null;
    public FoodListAdapter(Context context, List<Food> dataList) {
        super(context, R.layout.food_list, dataList);
        this.dataList = dataList;
        this.dataList.add(0,new Food());
        this.context = context;
    }
    @Override
    public int getCount() {
        return dataList.size();
    }

    public List<Food> getList()
    {
        return this.dataList ;
    }
    public void addToList(Food e)
    {
        dataList.add(e);
    }
    public void removeFromList(int e)
    {
        dataList.remove(e);
    }
    @Override
    public Food getItem(int position)
    {
        return dataList.get(position);
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView;
        if (position == 0) {
            rowView = inflater.inflate(R.layout.listheader_food, null, false);
            ImageView add = (ImageView) rowView.findViewById(R.id.adding);
        }
        else
            {
            rowView=inflater.inflate(R.layout.food_list, null, false);
            TextView txtTitle3 = (TextView) rowView.findViewById(R.id.Co2Amountb);
            ImageView edit = (ImageView) rowView.findViewById(R.id.editb);
            ImageView delete = (ImageView) rowView.findViewById(R.id.delete2b);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.food2);
            final Food itemInfo = dataList.get(position);
            txtTitle3.setText(String.valueOf(itemInfo.getCO2Amount()));
            imageView.setImageDrawable(itemInfo.getImg());
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete2(position);
                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edit2(position);
                }
            });
        }
        return rowView;
    }
    //delete item from the list
    public void delete2(int position) {
        new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_delete)
                .setTitle("Are you sure ?")
                .setMessage("Do you want to delete this item")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dataList.remove(position);
                        notifyDataSetChanged();

                    }
                })
                .setNegativeButton("No",null)
                .show();
    }
    //edit item in the list
      public void edit2(int position) {
        Intent intent = new Intent(context, EditItemActivityb.class);
        intent.putExtra("positionb",position);
        intent.putExtra("Typeb",dataList.get(position).getName());
        intent.putExtra("CO2Amountb",(String.valueOf(dataList.get(position).getCO2Amount())));
        ((FoodMain)context).startActivityForResult(intent,REQ_CODE);
    }
}
