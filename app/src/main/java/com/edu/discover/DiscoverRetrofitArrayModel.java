package com.edu.discover;

import com.google.gson.annotations.SerializedName;

public class DiscoverRetrofitArrayModel {
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("status")
    private String status;
    @SerializedName("title_description")
    private String title_description;
    @SerializedName("mime_type")
    private String mime_type;
    @SerializedName("header_url")
    private String header_url;
    @SerializedName("card_url")
    private String card_url;

    public String getAr_url() {
        return ar_url;
    }

    public void setAr_url(String ar_url) {
        this.ar_url = ar_url;
    }

    @SerializedName("ar_url")
    private String ar_url;

    public String getLong_description() {
        return long_description;
    }

    public void setLong_description(String long_description) {
        this.long_description = long_description;
    }



    @SerializedName("long_description")
    private String long_description;






    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle_description() {
        return title_description;
    }

    public void setTitle_description(String title_description) {
        this.title_description = title_description;
    }

    public String getMime_type() {
        return mime_type;
    }

    public void setMime_type(String mime_type) {
        this.mime_type = mime_type;
    }

    public String getHeader_url() {
        return header_url;
    }

    public void setHeader_url(String header_url) {
        this.header_url = header_url;
    }

    public String getCard_url() {
        return card_url;
    }

    public void setCard_url(String card_url) {
        this.card_url = card_url;
    }
}
