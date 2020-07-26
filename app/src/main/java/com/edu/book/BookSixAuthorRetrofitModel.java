package com.edu.book;

import com.google.gson.annotations.SerializedName;

public class BookSixAuthorRetrofitModel {


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    @SerializedName("description")
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @SerializedName("id")
    private String id;
    @SerializedName("profile_pic")
    private String profile_pic;
    @SerializedName("first_name")
    private String first_name;
    @SerializedName("last_name")
    private String last_name;




}
