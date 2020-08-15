package com.edu.browse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BrowseSecondUsingParentvRetrofitModel {



    @SerializedName("id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("title")
    private String name;


    public List<BrowseThirdRetrofitModel> getTop_books() {
        return top_books;
    }

    public void setTop_books(List<BrowseThirdRetrofitModel> top_books) {
        this.top_books = top_books;
    }

    @SerializedName("top_topics")
    private List<BrowseThirdRetrofitModel> top_books;





}
