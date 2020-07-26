package com.piechartpojo;

import com.barchartpojo.BarFourthColumn_categoryRetrofitModel;
import com.barchartpojo.BarThirdRetrofitModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PieSecondRetrofitModel {

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


    public List<PieThirdRetrofitModel> getPieThirdRetrofitModels() {
        return pieThirdRetrofitModels;
    }

    public void setPieThirdRetrofitModels(List<PieThirdRetrofitModel> pieThirdRetrofitModels) {
        this.pieThirdRetrofitModels = pieThirdRetrofitModels;
    }

    @SerializedName("pie_charts")
    private List<PieThirdRetrofitModel> pieThirdRetrofitModels;





}
