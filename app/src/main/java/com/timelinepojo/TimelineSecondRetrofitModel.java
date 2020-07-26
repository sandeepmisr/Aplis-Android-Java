package com.timelinepojo;

import com.google.gson.annotations.SerializedName;
import com.piechartpojo.PieThirdRetrofitModel;

import java.util.List;

public class TimelineSecondRetrofitModel {


    public List<TimelineThirdRetrofitModel> getTimelineThirdRetrofitModels() {
        return timelineThirdRetrofitModels;
    }

    public void setTimelineThirdRetrofitModels(List<TimelineThirdRetrofitModel> timelineThirdRetrofitModels) {
        this.timelineThirdRetrofitModels = timelineThirdRetrofitModels;
    }

    @SerializedName("timeline_entries")
    private List<TimelineThirdRetrofitModel> timelineThirdRetrofitModels;





}
