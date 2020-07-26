package com.edu.discover;

import com.edu.browse.BrowseSecondRetrofitModel;
import com.google.gson.annotations.SerializedName;

public class DiscoverDetailFirstRetrofitModell {

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @SerializedName("status")
    private String status;


    public DiscoverdetailSecondRetrofitModel getBrowseSecondRetrofitModel() {
        return discoverdetailSecondRetrofitModel;
    }

    public void setBrowseSecondRetrofitModel(DiscoverdetailSecondRetrofitModel discoverdetailSecondRetrofitModel) {
        this.discoverdetailSecondRetrofitModel = discoverdetailSecondRetrofitModel;
    }

    @SerializedName("data")
    private DiscoverdetailSecondRetrofitModel discoverdetailSecondRetrofitModel;



}
