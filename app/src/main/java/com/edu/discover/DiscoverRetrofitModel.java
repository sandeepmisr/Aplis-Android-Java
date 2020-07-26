package com.edu.discover;

import com.edu.retrofitapi.User;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DiscoverRetrofitModel {

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @SerializedName("status")
    private String status;

    public List<DiscoverRetrofitArrayModel> getData() {
        return data;
    }

    public void setData(List<DiscoverRetrofitArrayModel> data) {
        this.data = data;
    }

    @SerializedName("data")
    private List<DiscoverRetrofitArrayModel> data;

    private DiscoverRetrofitArrayModel discoverRetrofitArrayModel;


    public DiscoverRetrofitArrayModel getDiscoverRetrofitArrayModel() {
        return discoverRetrofitArrayModel;
    }

    public void setDiscoverRetrofitArrayModel(DiscoverRetrofitArrayModel discoverRetrofitArrayModel) {
        this.discoverRetrofitArrayModel = discoverRetrofitArrayModel;
    }
}
