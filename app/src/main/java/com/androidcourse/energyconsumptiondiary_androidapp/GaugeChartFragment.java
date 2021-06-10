package com.androidcourse.energyconsumptiondiary_androidapp;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.MyCo2FootprintManager;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Result;
import com.ekn.gruzer.gaugelibrary.HalfGauge;
import com.ekn.gruzer.gaugelibrary.Range;

public class GaugeChartFragment extends Fragment {

    MyCo2FootprintManager dbManager= MyCo2FootprintManager.getInstance();
    private static final String TAG = "GaugeFragment";
    private HalfGauge resultGauge=null;
    private String resultId=null;
    private Result result=null;
    public GaugeChartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        dbManager.openDataBase(getContext());
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreateView()");
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_gauge_chart, container, false);
        resultGauge=(HalfGauge) rootView.findViewById(R.id.halfGauge);
        Bundle args = getArguments();
        if(args!=null){
            resultId=args.getString("resultId");
            result=dbManager.getResultById(resultId);
        }
        //set colors
        setColors();
        //set data
        loadGaugeData();
        return rootView;
    }

    private void setColors() {
        Range range = new Range();
        range.setColor(getResources().getColor(R.color.bestGreen));
        range.setFrom( 0.0);
        range.setTo( 200.0);
        Range range2 = new Range();
        range2.setColor(getResources().getColor(R.color.warningYellow));
        range2.setFrom( 200.0);
        range2.setTo( 500.0);
        Range range3 = new Range();
        range3.setColor(getResources().getColor(R.color.warningOrange));
        range3.setFrom(500.0);
        range3.setTo(1000.0);
        Range range4 = new Range();
        range4.setColor(getResources().getColor(R.color.dangerRed));
        range4.setFrom(1000.0);
        range4.setTo(3000.0);
        //add color ranges to gauge
        resultGauge.addRange(range);
        resultGauge.addRange(range2);
        resultGauge.addRange(range3);
        resultGauge.addRange(range4);
        //set min max and current value
        resultGauge.setMinValue(0.0);
        resultGauge.setMaxValue(3000.0);

    }

    private void loadGaugeData(){
        resultGauge.setValue(result.getTotal());
    }

    @Override
    public void onResume() {
        MyCo2FootprintManager.getInstance().openDataBase(getContext());
        super.onResume();
    }

    @Override
    public void onPause() {
        MyCo2FootprintManager.getInstance().closeDataBase();
        super.onPause();
    }
}