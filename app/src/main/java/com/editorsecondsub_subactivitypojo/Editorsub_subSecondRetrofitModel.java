package com.editorsecondsub_subactivitypojo;

import com.editorsecondsubactivitypojo.ThirdRetrofitModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Editorsub_subSecondRetrofitModel {


    public List<Editorsub_subThirdRetrofitModel> getEditorsub_subThirdRetrofitModels() {
        return editorsub_subThirdRetrofitModels;
    }

    public void setEditorsub_subThirdRetrofitModels(List<Editorsub_subThirdRetrofitModel> editorsub_subThirdRetrofitModels) {
        this.editorsub_subThirdRetrofitModels = editorsub_subThirdRetrofitModels;
    }

    @SerializedName("data")
    private List<Editorsub_subThirdRetrofitModel> editorsub_subThirdRetrofitModels;



}
