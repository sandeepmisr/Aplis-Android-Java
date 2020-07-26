package com.barchartpojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BarFourthColumn_categoryRetrofitModel {


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("name")
    private String name;

    @SerializedName("column_values")
    private List<BarFourthColumn_valuesRetrofitModel> barFourthColumn_valuesRetrofitModels;

    public List<BarFourthColumn_valuesRetrofitModel> getBarFourthColumn_valuesRetrofitModels() {
        return barFourthColumn_valuesRetrofitModels;
    }

    public void setBarFourthColumn_valuesRetrofitModels(List<BarFourthColumn_valuesRetrofitModel> barFourthColumn_valuesRetrofitModels) {
        this.barFourthColumn_valuesRetrofitModels = barFourthColumn_valuesRetrofitModels;
    }
}
