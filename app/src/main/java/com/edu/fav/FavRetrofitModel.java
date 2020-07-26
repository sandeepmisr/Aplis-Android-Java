package com.edu.fav;

import com.edu.browse.BrowseThirdRetrofitModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavRetrofitModel {



    public List<FavSecondRetrofitModel> getData() {
        return data;
    }

    public void setData(List<FavSecondRetrofitModel> data) {
        this.data = data;
    }

    @SerializedName("data")
    private List<FavSecondRetrofitModel> data;




}
