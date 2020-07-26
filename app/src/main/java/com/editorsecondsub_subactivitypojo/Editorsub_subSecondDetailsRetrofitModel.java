package com.editorsecondsub_subactivitypojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Editorsub_subSecondDetailsRetrofitModel {


    public List<Editorsub_subThirdDetailsRetrofitModel> getEditorsub_subThirdDetailsRetrofitModels() {
        return editorsub_subThirdDetailsRetrofitModels;
    }

    public void setEditorsub_subThirdDetailsRetrofitModels(List<Editorsub_subThirdDetailsRetrofitModel> editorsub_subThirdDetailsRetrofitModels) {
        this.editorsub_subThirdDetailsRetrofitModels = editorsub_subThirdDetailsRetrofitModels;
    }

    @SerializedName("sub_topic_files")
    private List<Editorsub_subThirdDetailsRetrofitModel> editorsub_subThirdDetailsRetrofitModels;

}
