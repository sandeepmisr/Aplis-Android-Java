package com.edu.book;

import com.google.gson.annotations.SerializedName;

public class BookFifthCategoryBooksRetrofitModel {


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("name")
    private String name;




}
