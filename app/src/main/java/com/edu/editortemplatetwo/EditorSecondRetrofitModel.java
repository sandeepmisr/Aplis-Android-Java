package com.edu.editortemplatetwo;

import com.edu.book.BookSecondRetrofitModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EditorSecondRetrofitModel {


    public List<EditorThirdRetrofitModel> getEditorThirdRetrofitModels() {
        return editorThirdRetrofitModels;
    }

    public void setEditorThirdRetrofitModels(List<EditorThirdRetrofitModel> editorThirdRetrofitModels) {
        this.editorThirdRetrofitModels = editorThirdRetrofitModels;
    }

    @SerializedName("data")
    private List<EditorThirdRetrofitModel> editorThirdRetrofitModels;



}
