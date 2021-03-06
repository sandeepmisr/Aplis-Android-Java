package com.edu.browse;

import java.util.ArrayList;

class BrowseModel {
    private String id;


    public ArrayList<BrowseModel> getBrowseModels() {
        return browseModels;
    }

    public void setBrowseModels(ArrayList<BrowseModel> browseModels) {
        this.browseModels = browseModels;
    }

    private ArrayList<BrowseModel> browseModels;
    private String title;

    public String getRecenttitle() {
        return recenttitle;
    }

    public void setRecenttitle(String recenttitle) {
        this.recenttitle = recenttitle;
    }

    public String getRecentid() {
        return recentid;
    }

    public void setRecentid(String recentid) {
        this.recentid = recentid;
    }

    private String recenttitle;
    private String recentid;

    public String getRecentsubtitle() {
        return recentsubtitle;
    }

    public void setRecentsubtitle(String recentsubtitle) {
        this.recentsubtitle = recentsubtitle;
    }

    public String getRecentbookcover() {
        return recentbookcover;
    }

    public void setRecentbookcover(String recentbookcover) {
        this.recentbookcover = recentbookcover;
    }

    private String recentsubtitle;
    private String recentbookcover;

    public String getRecentdescriptior() {
        return recentdescriptior;
    }

    public void setRecentdescriptior(String recentdescriptior) {
        this.recentdescriptior = recentdescriptior;
    }

    private String recentdescriptior;
    private String sub_title;
    private String description;
    private String image;
    private String bg_image;
    private String color_code;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBg_image() {
        return bg_image;
    }

    public void setBg_image(String bg_image) {
        this.bg_image = bg_image;
    }

    public String getColor_code() {
        return color_code;
    }

    public void setColor_code(String color_code) {
        this.color_code = color_code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
