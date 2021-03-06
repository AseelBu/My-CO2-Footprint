package com.androidcourse.energyconsumptiondiary_androidapp;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
public class EntryDateFragment extends Fragment {
    private DatePicker datePicker;
    private Date selectedDate =Calendar.getInstance().getTime();
    public Date getSelectedDate() {
        return selectedDate;
    }

    public EntryDateFragment() {
    }

    public static EntryDateFragment newInstance() {
        EntryDateFragment fragment = new EntryDateFragment();
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_entry_date, container, false);
        datePicker=(DatePicker)v.findViewById(R.id.entryDateCal);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,0);
        datePicker.setMaxDate(calendar.getTimeInMillis());
        datePicker.init(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

               selectedDate =new GregorianCalendar(year,monthOfYear,dayOfMonth).getTime();
            }
        });
        return v;
    }
}