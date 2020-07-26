package com.edu.discover;

import com.google.gson.annotations.SerializedName;

public class DiscoverdetailThirdRetrofitModel {

    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("book_cover")
    private String book_cover;

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


    public String getBook_cover() {
        return book_cover;
    }

    public void setBook_cover(String book_cover) {
        this.book_cover = book_cover;
    }

}
