package com.androidcourse.energyconsumptiondiary_androidapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.androidcourse.energyconsumptiondiary_androidapp.Model.Tip;
import com.androidcourse.energyconsumptiondiary_androidapp.R;

import org.w3c.dom.Text;

import java.util.List;

public class TipAdapter extends RecyclerView.Adapter<TipAdapter.TipViewHolder> {
    private List<Tip> tipsList;

    public TipAdapter(List<Tip> tipsList) {
        this.tipsList = tipsList;
    }

    @Override
    public int getItemCount() {
        return tipsList.size();
    }

    @Override
    public void onBindViewHolder(TipViewHolder tipViewHolder, int position) {

        Tip tip = tipsList.get(position);
        tipViewHolder.setData(tip);
    }

    @Override
    public TipViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.tip_card_layout, viewGroup, false);

        return new TipViewHolder(itemView);
    }

    public class TipViewHolder extends RecyclerView.ViewHolder{

        private TextView vTitle;
        private TextView vContent;
        private ImageView vImage;

        private Tip tip=null;

        public TipViewHolder(View v) {
            super(v);
            vTitle=(TextView) v.findViewById(R.id.tipTitle);
            vContent=(TextView) v.findViewById(R.id.tipContent);
            vImage=(ImageView) v.findViewById(R.id.tipImg);
        }

        public void setData(Tip tip){
            this.tip = tip;
            vTitle.setText(tip.getTitle());
            vContent.setText(tip.getContent());
            vImage.setImageDrawable(tip.getImg());

        }
    }
}
