package com.androidcourse.energyconsumptiondiary_androidapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.androidcourse.energyconsumptiondiary_androidapp.Model.CO2Impacter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.ElectricalHouseSupplies;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Food;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Services;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Transportation;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.TypeEntry;
import com.androidcourse.energyconsumptiondiary_androidapp.R;

import java.util.List;

public class EntryRecyclerAdapter extends RecyclerView.Adapter<EntryRecyclerAdapter.entryCardViewHolder>{

    List<? extends CO2Impacter> impacters;

    public EntryRecyclerAdapter(List<? extends CO2Impacter> impacters) {
        this.impacters = impacters;
    }

    @Override
    public void onBindViewHolder( entryCardViewHolder holder, int position) {
        CO2Impacter impacterItem = impacters.get(position);
        holder.setData(impacterItem);

    }

    @Override
    public int getItemCount() {
        return impacters.size();
    }

    @Override
    public entryCardViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.entry_card_recycler, parent, false);

        return new EntryRecyclerAdapter.entryCardViewHolder(itemView);
    }


    public class entryCardViewHolder extends RecyclerView.ViewHolder{
        CO2Impacter impacterItem=null;
        TypeEntry cardData=new TypeEntry();
        int amount =-1;
        NumberPicker numPicker ;
        TextView cardId;

        public entryCardViewHolder( View v) {
            super(v);
            numPicker=(NumberPicker)v.findViewById(R.id.amountValue);
            numPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    cardData.setValue(newVal);
                }
            });
            cardId=(TextView) v.findViewById(R.id.cardId);
        }

        public void setData(CO2Impacter impacterItem) {
            //check item type
            if(impacterItem instanceof Transportation) {
                this.impacterItem=(Transportation)impacterItem;
            }
            else if(impacterItem instanceof Food){
                this.impacterItem=(Food)impacterItem;
            }
            else if(impacterItem instanceof ElectricalHouseSupplies){
                this.impacterItem=(ElectricalHouseSupplies)impacterItem;
            }
            else if(impacterItem instanceof Services){
                this.impacterItem=(Services)impacterItem;
            }
        }
    }

}
