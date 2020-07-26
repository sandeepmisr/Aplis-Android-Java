package com.editorsecondsubactivitypojo;

import com.edu.editortemplatetwo.EditorThirdRetrofitModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SecondRetrofitModel {


    public List<ThirdRetrofitModel> getThirdRetrofitModels() {
        return thirdRetrofitModels;
    }

    public void setThirdRetrofitModels(List<ThirdRetrofitModel> thirdRetrofitModels) {
        this.thirdRetrofitModels = thirdRetrofitModels;
    }

    @SerializedName("data")
    private List<ThirdRetrofitModel> thirdRetrofitModels;



}
