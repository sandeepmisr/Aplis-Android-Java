package com.editorsub_sub_subpojo;

import com.editorsecondsubactivitypojo.ThirdRetrofitModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EditorSub_Sub_SubFirstRetrofitModel {


    public List<EditorSub_Sub_SubSecondRetrofitModel> getEditorSub_sub_subSecondRetrofitModels() {
        return editorSub_sub_subSecondRetrofitModels;
    }

    public void setEditorSub_sub_subSecondRetrofitModels(List<EditorSub_Sub_SubSecondRetrofitModel> editorSub_sub_subSecondRetrofitModels) {
        this.editorSub_sub_subSecondRetrofitModels = editorSub_sub_subSecondRetrofitModels;
    }

    @SerializedName("data")
    private List<EditorSub_Sub_SubSecondRetrofitModel> editorSub_sub_subSecondRetrofitModels;



}
