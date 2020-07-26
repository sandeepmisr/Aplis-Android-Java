package com.edu.discover;


public class Books {
    private String id;

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    private String book_name;
    private String book_cover;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setbook_cover(String book_cover) {
        this.book_cover = book_cover;
    }

    public String getBook_cover() {
        return book_cover;
    }
}
