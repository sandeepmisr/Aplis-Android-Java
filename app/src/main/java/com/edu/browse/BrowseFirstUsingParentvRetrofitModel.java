package com.edu.browse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BrowseFirstUsingParentvRetrofitModel {

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @SerializedName("status")
    private String status;


    public BrowseSecondBookModel getData() {
        return data;
    }

    public void setData(BrowseSecondBookModel data) {
        this.data = data;
    }

    @SerializedName("data")
    BrowseSecondBookModel data;





}
