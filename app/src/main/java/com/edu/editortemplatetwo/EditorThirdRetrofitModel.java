package com.edu.editortemplatetwo;

import com.google.gson.annotations.SerializedName;

public class EditorThirdRetrofitModel {


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

    public String getTopic_cover() {
        return topic_cover;
    }

    public void setTopic_cover(String topic_cover) {
        this.topic_cover = topic_cover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("sub_title")
    private String sub_title;

    @SerializedName("topic_cover")
    private String topic_cover;

    @SerializedName("description")
    private String description;


    @SerializedName("bg_image")
    private String bg_image;

    @SerializedName("color_code")
    private String color_code;





}
