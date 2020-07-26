package com.edu.book;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookSecondRetrofitModel {

    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("sub_title")
    private String sub_title;
    @SerializedName("description")
    private String description;
    @SerializedName("min_age")
    private String min_age;
    @SerializedName("max_age")
    private String max_age;
    @SerializedName("book_cover")
    private String book_cover;
    @SerializedName("cover")
    private String cover;
    @SerializedName("isbn_code")
    private String isbn_code;

    public List<BookThirdRetrofitModel> getBookthirdRetrofitModel() {
        return bookthirdRetrofitModel;
    }

    public void setBookthirdRetrofitModel(List<BookThirdRetrofitModel> bookthirdRetrofitModel) {
        this.bookthirdRetrofitModel = bookthirdRetrofitModel;
    }

    @SerializedName("fav")
    private List<BookThirdRetrofitModel> bookthirdRetrofitModel;

    public BookFifthCategoryBooksRetrofitModel getBookFifthCategoryRetrofitModel() {
        return bookFifthCategoryRetrofitModel;
    }

    public void setBookFifthCategoryRetrofitModel(BookFifthCategoryBooksRetrofitModel bookFifthCategoryRetrofitModel) {
        this.bookFifthCategoryRetrofitModel = bookFifthCategoryRetrofitModel;
    }

    @SerializedName("category")
    private BookFifthCategoryBooksRetrofitModel bookFifthCategoryRetrofitModel;

    public List<BookFourthOtherBooksRetrofitModel> getBookFourthOtherBooksRetrofitModel() {
        return bookFourthOtherBooksRetrofitModel;
    }

    public void setBookFourthOtherBooksRetrofitModel(List<BookFourthOtherBooksRetrofitModel> bookFourthOtherBooksRetrofitModel) {
        this.bookFourthOtherBooksRetrofitModel = bookFourthOtherBooksRetrofitModel;
    }

    @SerializedName("other_books")
    private List<BookFourthOtherBooksRetrofitModel> bookFourthOtherBooksRetrofitModel;

    public BookSixAuthorRetrofitModel getBookSixAuthorRetrofitModel() {
        return bookSixAuthorRetrofitModel;
    }

    public void setBookSixAuthorRetrofitModel(BookSixAuthorRetrofitModel bookSixAuthorRetrofitModel) {
        this.bookSixAuthorRetrofitModel = bookSixAuthorRetrofitModel;
    }

    @SerializedName("author")
    private BookSixAuthorRetrofitModel  bookSixAuthorRetrofitModel ;



    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @SerializedName("language")
    private String language;


    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @SerializedName("created_at")
    private String created_at;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMin_age() {
        return min_age;
    }

    public void setMin_age(String min_age) {
        this.min_age = min_age;
    }

    public String getMax_age() {
        return max_age;
    }

    public void setMax_age(String max_age) {
        this.max_age = max_age;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getIsbn_code() {
        return isbn_code;
    }

    public void setIsbn_code(String isbn_code) {
        this.isbn_code = isbn_code;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @SerializedName("tags")
    private String tags;
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



}
