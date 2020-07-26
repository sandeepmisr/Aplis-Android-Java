package com.viewactivitypojo;

import com.edu.editortemplatetwo.EditorSecondRetrofitModel;
import com.google.gson.annotations.SerializedName;

public class ViewActivityThirdModel {


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @SerializedName("url")
    private String  url;



}
