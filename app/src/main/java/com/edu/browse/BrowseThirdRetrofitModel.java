package com.edu.browse;

import com.edu.discover.DiscoverRetrofitArrayModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BrowseThirdRetrofitModel {

    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("sub_title")
    private String sub_title;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @SerializedName("description")
    private String description;

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

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getBook_cover() {
        return book_cover;
    }

    public void setBook_cover(String book_cover) {
        this.book_cover = book_cover;
    }

    @SerializedName("topic_cover")
    private String book_cover;
}
