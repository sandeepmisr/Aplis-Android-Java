package com.edu.editortemplatetwo;

import com.edu.book.BookSecondRetrofitModel;
import com.google.gson.annotations.SerializedName;

public class EditorFirstRetrofitModel {


    public EditorSecondRetrofitModel getEditorSecondRetrofitModel() {
        return editorSecondRetrofitModel;
    }

    public void setEditorSecondRetrofitModel(EditorSecondRetrofitModel editorSecondRetrofitModel) {
        this.editorSecondRetrofitModel = editorSecondRetrofitModel;
    }

    @SerializedName("data")
    private EditorSecondRetrofitModel  editorSecondRetrofitModel;



}
