package com.edu.browse;

import com.edu.discover.DiscoverRetrofitArrayModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BrowseFirstRetrofitModel {

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @SerializedName("status")
    private String status;


    public BrowseSecondRetrofitModel getBrowseSecondRetrofitModel() {
        return browseSecondRetrofitModel;
    }

    public void setBrowseSecondRetrofitModel(BrowseSecondRetrofitModel browseSecondRetrofitModel) {
        this.browseSecondRetrofitModel = browseSecondRetrofitModel;
    }

    @SerializedName("data")
    private BrowseSecondRetrofitModel browseSecondRetrofitModel;



}
