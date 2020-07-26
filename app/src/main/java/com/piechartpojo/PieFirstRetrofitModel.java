package com.piechartpojo;

import com.barchartpojo.BarSecondRetrofitModel;
import com.google.gson.annotations.SerializedName;

public class PieFirstRetrofitModel {


    public PieSecondRetrofitModel getPieSecondRetrofitModel() {
        return pieSecondRetrofitModel;
    }

    public void setPieSecondRetrofitModel(PieSecondRetrofitModel pieSecondRetrofitModel) {
        this.pieSecondRetrofitModel = pieSecondRetrofitModel;
    }

    @SerializedName("data")
    private PieSecondRetrofitModel pieSecondRetrofitModel;



}
