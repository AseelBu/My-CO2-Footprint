package com.androidcourse.energyconsumptiondiary_androidapp;

public interface PrevResultsFragmentListener {
    public void onResultSelected(BarChartFragment prevFragment,int resultId);
    public void onNothingSelected(BarChartFragment prevFragment);
}
