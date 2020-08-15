package com.edu.browse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Parnet_CategorySecondRetrofitModel {



    public List<ParentCatgoery_ThirdRetrofitModel> getData() {
        return data;
    }

    public void setData(List<ParentCatgoery_ThirdRetrofitModel> data) {
        this.data = data;
    }

    @SerializedName("data")
    private List<ParentCatgoery_ThirdRetrofitModel> data;




}
