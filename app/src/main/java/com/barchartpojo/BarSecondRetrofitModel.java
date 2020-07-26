package com.barchartpojo;

import com.editorsecondsubactivitypojo.ThirdRetrofitModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BarSecondRetrofitModel {

    @SerializedName("heading")
    private String heading;
    @SerializedName("sub_heading")
    private String sub_heading;

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getSub_heading() {
        return sub_heading;
    }

    public void setSub_heading(String sub_heading) {
        this.sub_heading = sub_heading;
    }

    public String getHtml_content() {
        return html_content;
    }

    public void setHtml_content(String html_content) {
        this.html_content = html_content;
    }

    @SerializedName("html_content")
    private String html_content;


    public List<BarThirdRetrofitModel> getBarThirdRetrofitModels() {
        return barThirdRetrofitModels;
    }

    public void setBarThirdRetrofitModels(List<BarThirdRetrofitModel> barThirdRetrofitModels) {
        this.barThirdRetrofitModels = barThirdRetrofitModels;
    }

    @SerializedName("columns")
    private List<BarThirdRetrofitModel> barThirdRetrofitModels;


    public List<BarFourthColumn_categoryRetrofitModel> getBarFourthColumn_categoryRetrofitModels() {
        return barFourthColumn_categoryRetrofitModels;
    }

    public void setBarFourthColumn_categoryRetrofitModels(List<BarFourthColumn_categoryRetrofitModel> barFourthColumn_categoryRetrofitModels) {
        this.barFourthColumn_categoryRetrofitModels = barFourthColumn_categoryRetrofitModels;
    }

    @SerializedName("column_categories")
    private List<BarFourthColumn_categoryRetrofitModel> barFourthColumn_categoryRetrofitModels;



}
