package com.androidcourse.energyconsumptiondiary_androidapp;
import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimePickerDialogFragment extends DialogFragment {
private final static String TITLE="Title";
    private String title="";
    private String chosenTime="";
    private TimePicker tp;
    private Button saveBtn;
    private Button cancelBtn;
    TimePickerListener mListener;
    private Activity activity;
    public TimePickerDialogFragment() {
        // Required empty public constructor
    }

    public static TimePickerDialogFragment newInstance(String title) {
        TimePickerDialogFragment fragment = new TimePickerDialogFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity=getActivity();
        try {
            // Instantiate the MyAlertDialogFragmentListener so we can send events to the host
            mListener = (TimePickerListener) activity;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (getArguments() != null) {
            this.title = getArguments().getString(TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_time_picker_dialog, container, false);
        getDialog().setTitle(this.title);
        tp=(TimePicker)view.findViewById(R.id.timePickerSettings) ;
        chosenTime= new SimpleDateFormat("HH:mm",Locale.getDefault()).format(new Date());
        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                chosenTime=String.valueOf(hourOfDay)+":"+String.valueOf(minute);
            }
        });
        saveBtn=(Button) view.findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(activity, "Time set to " + chosenTime,Toast.LENGTH_SHORT).show();
                mListener.onDialogPositiveClick(TimePickerDialogFragment.this);
                dismiss();
            }
        });
        cancelBtn=(Button) view.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onDialogNegativeClick(TimePickerDialogFragment.this);
                dismiss();
            }
        });
        return view;
    }

    public String getChosenTime()
    {
        return this.chosenTime;
    }
}