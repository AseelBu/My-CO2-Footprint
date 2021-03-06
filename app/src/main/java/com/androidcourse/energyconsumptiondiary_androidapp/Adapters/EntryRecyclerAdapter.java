package com.androidcourse.energyconsumptiondiary_androidapp.Adapters;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.androidcourse.energyconsumptiondiary_androidapp.EntryActivity;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Co2Impacter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.ElectricalHouseSupplies;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Food;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Service;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Transportation;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.TypeEntry;
import com.androidcourse.energyconsumptiondiary_androidapp.R;
import com.androidcourse.energyconsumptiondiary_androidapp.core.ImpactType;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.HashSet;
import java.util.List;

public class EntryRecyclerAdapter extends RecyclerView.Adapter<EntryRecyclerAdapter.EntryCardViewHolder> {
    List<? extends Co2Impacter> impacters;
    ImpactType impacterType;
    HashSet<TypeEntry> entries = new HashSet<>();
    HashSet<TypeEntry> prevEntries = new HashSet<>();
    Context context;

    public EntryRecyclerAdapter(Context context, List<? extends Co2Impacter> impacters, ImpactType impacterType) {
        this.impacters = impacters;
        this.impacterType = impacterType;
        this.context = context;
        this.prevEntries = new HashSet<>(((EntryActivity) context).getEntryData().getEntries());
    }

    @Override
    public int getItemCount() {
        return impacters.size();
    }

    @Override
    public EntryCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.entry_card_recycler, parent, false);

        return new EntryCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EntryCardViewHolder holder, int position) {
        Log.i("EntryAdapter", "entering onBindView");
        Co2Impacter impacterItem = impacters.get(position);
        holder.setData(impacterItem);
    }

    private void updateEntry(TypeEntry newCard) {
        entries.remove(newCard);
        entries.add(newCard);
    }

    public HashSet<TypeEntry> getEntries() {
        return entries;
    }

    private boolean checkIfValueSet(String id) {
        for (TypeEntry te : prevEntries) {
            if (te.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public class EntryCardViewHolder extends RecyclerView.ViewHolder {
        private Co2Impacter impacterItem = null;

        private TypeEntry cardData = new TypeEntry();
        private int amount = -1;
        private NumberPicker numPicker;
        private TextView cardId;
        private TextView question;
        private TextView txtUnit;
        private ImageView cardImg;

        public EntryCardViewHolder(View v) {
            super(v);
            numPicker = (NumberPicker) v.findViewById(R.id.amountValue);
            numPicker.setMinValue(0);
            numPicker.setMaxValue(200);
            numPicker.setWrapSelectorWheel(false);
            numPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    cardData.setValue(newVal);
                    cardData.setType(impacterType);
                    if (newVal == 0) {
                        handleZeroValue();
                    } else if (!entries.add(cardData)) {

                        updateEntry(cardData);
                        ((EntryActivity) context).setResultsEnabled(true);

                    }
                    //if it's not entry update
                    else {
                        ((EntryActivity) context).setResultsEnabled(true);
                        if (impacterType.equals((ImpactType.SERVICES))) {
                            ((EntryActivity) context).enableResults();
                        }

                    }
                    Log.i("EntryAdapter", "value choosed= " + cardData.getValue());
                }
            });
            cardId = (TextView) v.findViewById(R.id.cardId);
            question = (TextView) v.findViewById(R.id.questionTxt);
            txtUnit = (TextView) v.findViewById(R.id.txtUnit);
            cardImg = (ImageView) v.findViewById(R.id.entryImg);
        }

        public void handleZeroValue() {
            entries.remove(cardData);
            ((EntryActivity) context).removeEntryFromEntryData(cardData);
            if (((EntryActivity) context).getEntryData().getEntries().size() > 0) {
                ((EntryActivity) context).setResultsEnabled(true);

            } else {
                if (impacterType.equals((ImpactType.SERVICES))) {
                    ((EntryActivity) context).disableResults();
                }
                ((EntryActivity) context).setResultsEnabled(false);
            }

        }


        public void setData(Co2Impacter impacterItem) {
            Log.i("EntryAdapter", "entering setData");
            cardData.setId(impacterItem.getImpacterID());
            question.setText(impacterItem.getQuestion());
            txtUnit.setText(impacterItem.getUnit().name().toLowerCase());
            cardId.setText(String.valueOf(impacterItem.getImpacterID()));


            String imageUrl = impacterItem.getUrlImage();
            //set picture
            if(imageUrl!=null) {
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
                                            .into(cardImg);
                                } else {
                                    System.out.println("Getting download url was not successful." +
                                            task.getException());
                                }
                            }
                        });
            }
//            cardImg.setImageDrawable(new BitmapDrawable(context.getResources(), impacterItem.getImg()));
            prevEntries = new HashSet<>(((EntryActivity) context).getEntryData().getEntries());
            if (checkIfValueSet(impacterItem.getImpacterID())) {

                for (TypeEntry te : prevEntries) {
                    if (te.getId().equals(impacterItem.getImpacterID())) {
                        numPicker.setValue(te.getValue());
                    }
                }
            } else {
                numPicker.setValue(0);
            }
            //check item type
            if (impacterItem instanceof Transportation) {
                this.impacterItem = (Transportation) impacterItem;
            } else if (impacterItem instanceof Food) {
                this.impacterItem = (Food) impacterItem;
            } else if (impacterItem instanceof ElectricalHouseSupplies) {
                this.impacterItem = (ElectricalHouseSupplies) impacterItem;
            } else if (impacterItem instanceof Service) {
                this.impacterItem = (Service) impacterItem;
            }
        }

    }
}
