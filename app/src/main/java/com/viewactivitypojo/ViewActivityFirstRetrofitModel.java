package com.viewactivitypojo;

import com.edu.editortemplatetwo.EditorSecondRetrofitModel;
import com.google.gson.annotations.SerializedName;

public class ViewActivityFirstRetrofitModel {


    public ViewActivitySecondModel getViewActivitySecondModel() {
        return viewActivitySecondModel;
    }

    public void setViewActivitySecondModel(ViewActivitySecondModel viewActivitySecondModel) {
        this.viewActivitySecondModel = viewActivitySecondModel;
    }

    @SerializedName("data")
    private ViewActivitySecondModel  viewActivitySecondModel;



}
