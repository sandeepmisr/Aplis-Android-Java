package com.editorsub_sub_subpojo;

import com.editorsecondsub_subactivitypojo.Editorsub_subSecondDetailsRetrofitModel;
import com.google.gson.annotations.SerializedName;

public class Editorsub_sub_subFirstDetailsRetrofitModel {

    public Editorsub_sub_subSecondDetailsRetrofitModel getEditorsub_sub_subSecondDetailsRetrofitModel() {
        return editorsub_sub_subSecondDetailsRetrofitModel;
    }

    public void setEditorsub_sub_subSecondDetailsRetrofitModel(Editorsub_sub_subSecondDetailsRetrofitModel editorsub_sub_subSecondDetailsRetrofitModel) {
        this.editorsub_sub_subSecondDetailsRetrofitModel = editorsub_sub_subSecondDetailsRetrofitModel;
    }

    @SerializedName("data")
    private Editorsub_sub_subSecondDetailsRetrofitModel editorsub_sub_subSecondDetailsRetrofitModel;

}
