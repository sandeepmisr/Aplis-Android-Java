package com.editorsub_sub_subpojo;

import com.google.gson.annotations.SerializedName;

public class Editorsub_sub_subThirdDetailsRetrofitModel {




    @SerializedName("id")
    private String id;

    @SerializedName("mime_type")
    private String mime_type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMime_type() {
        return mime_type;
    }

    public void setMime_type(String mime_type) {
        this.mime_type = mime_type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @SerializedName("url")
    private String url;


}
