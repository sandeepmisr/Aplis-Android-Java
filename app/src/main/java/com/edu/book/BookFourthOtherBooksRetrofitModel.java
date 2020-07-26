package com.edu.book;

import com.google.gson.annotations.SerializedName;

public class BookFourthOtherBooksRetrofitModel {


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @SerializedName("id")
    private String id;

    @SerializedName("book_cover")
    private String book_cover;



    public String getBook_cover() {
        return book_cover;
    }

    public void setBook_cover(String book_cover) {
        this.book_cover = book_cover;
    }


}
