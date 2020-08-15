package com.edu.browse;

import com.google.gson.annotations.SerializedName;

public class ParentCatgoery_ThirdRetrofitModel {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("type")
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return name;
    }

    public void setTitle(String title) {
        this.name = title;
    }

    public String getSub_title() {
        return type;
    }

    public void setSub_title(String sub_title) {
        this.type = sub_title;
    }
}
