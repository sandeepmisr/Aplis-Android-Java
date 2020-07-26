package com.edu.browse;

import com.edu.discover.DiscoverRetrofitArrayModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BrowseSecondRetrofitModel {



    public List<BrowseThirdRetrofitModel> getData() {
        return data;
    }

    public void setData(List<BrowseThirdRetrofitModel> data) {
        this.data = data;
    }

    @SerializedName("data")
    private List<BrowseThirdRetrofitModel> data;

    public String getLast_page() {
        return last_page;
    }

    public void setLast_page(String last_page) {
        this.last_page = last_page;
    }

    @SerializedName("last_page")
    private String last_page;


}
