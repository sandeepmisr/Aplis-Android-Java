package com.edu.discover;

import com.edu.browse.BrowseThirdRetrofitModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DiscoverdetailSecondRetrofitModel {



    public List<DiscoverdetailThirdRetrofitModel> getData() {
        return data;
    }

    public void setData(List<DiscoverdetailThirdRetrofitModel> data) {
        this.data = data;
    }

    @SerializedName("data")
    private List<DiscoverdetailThirdRetrofitModel> data;

    public String getLast_page() {
        return last_page;
    }

    public void setLast_page(String last_page) {
        this.last_page = last_page;
    }

    @SerializedName("last_page")
    private String last_page;


}
