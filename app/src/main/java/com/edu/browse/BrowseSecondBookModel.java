package com.edu.browse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BrowseSecondBookModel {


    public List<BrowseSecondUsingParentvRetrofitModel> getBrowseSecondUsingParentvRetrofitModels() {
        return browseSecondUsingParentvRetrofitModels;
    }

    public void setBrowseSecondUsingParentvRetrofitModels(List<BrowseSecondUsingParentvRetrofitModel> browseSecondUsingParentvRetrofitModels) {
        this.browseSecondUsingParentvRetrofitModels = browseSecondUsingParentvRetrofitModels;
    }

    @SerializedName("data")
    private List<BrowseSecondUsingParentvRetrofitModel> browseSecondUsingParentvRetrofitModels;



}
