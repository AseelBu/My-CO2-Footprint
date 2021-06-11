package com.androidcourse.energyconsumptiondiary_androidapp;

public interface PrevResultsFragmentListener {
    public void onResultSelected(BarChartFragment prevFragment,String resultId);
    public void onNothingSelected(BarChartFragment prevFragment);
}
