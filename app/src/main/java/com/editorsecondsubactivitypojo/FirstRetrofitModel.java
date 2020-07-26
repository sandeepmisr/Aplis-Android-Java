package com.editorsecondsubactivitypojo;

import com.edu.editortemplatetwo.EditorSecondRetrofitModel;
import com.google.gson.annotations.SerializedName;

public class FirstRetrofitModel {


    public EditorSecondRetrofitModel getEditorSecondRetrofitModel() {
        return editorSecondRetrofitModel;
    }

    public void setEditorSecondRetrofitModel(EditorSecondRetrofitModel editorSecondRetrofitModel) {
        this.editorSecondRetrofitModel = editorSecondRetrofitModel;
    }

    @SerializedName("data")
    private EditorSecondRetrofitModel  editorSecondRetrofitModel;



}
