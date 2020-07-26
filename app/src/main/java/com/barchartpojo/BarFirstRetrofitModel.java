package com.barchartpojo;

import com.edu.editortemplatetwo.EditorSecondRetrofitModel;
import com.google.gson.annotations.SerializedName;

public class BarFirstRetrofitModel {


    public BarSecondRetrofitModel getBarSecondRetrofitModel() {
        return barSecondRetrofitModel;
    }

    public void setBarSecondRetrofitModel(BarSecondRetrofitModel barSecondRetrofitModel) {
        this.barSecondRetrofitModel = barSecondRetrofitModel;
    }

    @SerializedName("data")
    private BarSecondRetrofitModel barSecondRetrofitModel;



}
