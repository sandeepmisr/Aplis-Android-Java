package com.editorsub_sub_subpojo;

import com.editorsecondsub_subactivitypojo.Editorsub_subThirdDetailsRetrofitModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Editorsub_sub_subSecondDetailsRetrofitModel {


    @SerializedName("sub_sub_topic_files")
    private List<Editorsub_sub_subThirdDetailsRetrofitModel> editorsub_sub_subThirdDetailsRetrofitModels;

    public List<Editorsub_sub_subThirdDetailsRetrofitModel> getEditorsub_sub_subThirdDetailsRetrofitModels() {
        return editorsub_sub_subThirdDetailsRetrofitModels;
    }

    public void setEditorsub_sub_subThirdDetailsRetrofitModels(List<Editorsub_sub_subThirdDetailsRetrofitModel> editorsub_sub_subThirdDetailsRetrofitModels) {
        this.editorsub_sub_subThirdDetailsRetrofitModels = editorsub_sub_subThirdDetailsRetrofitModels;
    }
}
