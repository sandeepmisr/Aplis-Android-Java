package com.timelinepojo;

import com.google.gson.annotations.SerializedName;

public class TimelineThirdRetrofitModel {


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

    @SerializedName("description")
    private String description;

    @SerializedName("date")
    private String date;

    @SerializedName("time")
    private String time;

    @SerializedName("year")
    private String year;

    @SerializedName("mime_type")
    private String mime_type;

    @SerializedName("url")
    private String card_url;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMime_type() {
        return mime_type;
    }

    public void setMime_type(String mime_type) {
        this.mime_type = mime_type;
    }

    public String getCard_url() {
        return card_url;
    }

    public void setCard_url(String card_url) {
        this.card_url = card_url;
    }

    public String getAr_url() {
        return Ar_url;
    }

    public void setAr_url(String ar_url) {
        Ar_url = ar_url;
    }

    @SerializedName("Ar_url")
    private String Ar_url;









}
