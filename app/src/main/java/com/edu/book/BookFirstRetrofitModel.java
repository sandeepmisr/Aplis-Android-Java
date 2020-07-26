package com.edu.book;

import com.edu.browse.BrowseSecondRetrofitModel;
import com.google.gson.annotations.SerializedName;

public class BookFirstRetrofitModel {

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @SerializedName("status")
    private String status;


    public BookSecondRetrofitModel getBookSecondRetrofitModel() {
        return bookSecondRetrofitModel;
    }

    public void setBookSecondRetrofitModel(BookSecondRetrofitModel bookSecondRetrofitModel) {
        this.bookSecondRetrofitModel = bookSecondRetrofitModel;
    }

    @SerializedName("data")
    private BookSecondRetrofitModel bookSecondRetrofitModel;



}
