package com.edu.browse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ParentCategory_FirstRetrofitModel {

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @SerializedName("status")
    private String status;


    public List<ParentCatgoery_ThirdRetrofitModel> getParentCatgoery_thirdRetrofitModelList() {
        return parentCatgoery_thirdRetrofitModelList;
    }

    public void setParentCatgoery_thirdRetrofitModelList(List<ParentCatgoery_ThirdRetrofitModel> parentCatgoery_thirdRetrofitModelList) {
        this.parentCatgoery_thirdRetrofitModelList = parentCatgoery_thirdRetrofitModelList;
    }

    @SerializedName("data")
    private List<ParentCatgoery_ThirdRetrofitModel> parentCatgoery_thirdRetrofitModelList;



}
