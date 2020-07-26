package com.piechartpojo;

import com.google.gson.annotations.SerializedName;

public class PieThirdRetrofitModel {


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    @SerializedName("id")
    private String id;

    @SerializedName("heading")
    private String heading;

 @SerializedName("sub_heading")
    private String sub_heading;
 @SerializedName("url")
    private String url;
 @SerializedName("description")
    private String description;
 @SerializedName("numaric_value")
    private String numaric_value;

 @SerializedName("color_code")
    private String color_code;

    public String getMime_type() {
        return mime_type;
    }

    public void setMime_type(String mime_type) {
        this.mime_type = mime_type;
    }

    @SerializedName("mime_type")
    private String mime_type;

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getSub_heading() {
        return sub_heading;
    }

    public void setSub_heading(String sub_heading) {
        this.sub_heading = sub_heading;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumaric_value() {
        return numaric_value;
    }

    public void setNumaric_value(String numaric_value) {
        this.numaric_value = numaric_value;
    }

    public String getColor_code() {
        return color_code;
    }

    public void setColor_code(String color_code) {
        this.color_code = color_code;
    }

    public String getAr_url() {
        return ar_url;
    }

    public void setAr_url(String ar_url) {
        this.ar_url = ar_url;
    }

    @SerializedName("ar_url")
    private String ar_url;








}
