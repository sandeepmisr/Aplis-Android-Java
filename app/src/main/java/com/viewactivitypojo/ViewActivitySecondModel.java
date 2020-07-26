package com.viewactivitypojo;

import com.edu.editortemplatetwo.EditorSecondRetrofitModel;
import com.google.gson.annotations.SerializedName;

public class ViewActivitySecondModel {


    public ViewActivityThirdModel getViewActivityThirdModel() {
        return viewActivityThirdModel;
    }

    public void setViewActivityThirdModel(ViewActivityThirdModel viewActivityThirdModel) {
        this.viewActivityThirdModel = viewActivityThirdModel;
    }

    @SerializedName("website")
    private ViewActivityThirdModel  viewActivityThirdModel;



}
