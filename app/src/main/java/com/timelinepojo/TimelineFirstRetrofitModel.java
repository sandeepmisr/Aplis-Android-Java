package com.timelinepojo;

import com.google.gson.annotations.SerializedName;
import com.piechartpojo.PieSecondRetrofitModel;

public class TimelineFirstRetrofitModel {


    public TimelineSecondRetrofitModel getTimelineSecondRetrofitModel() {
        return timelineSecondRetrofitModel;
    }

    public void setTimelineSecondRetrofitModel(TimelineSecondRetrofitModel timelineSecondRetrofitModel) {
        this.timelineSecondRetrofitModel = timelineSecondRetrofitModel;
    }

    @SerializedName("data")
    private TimelineSecondRetrofitModel timelineSecondRetrofitModel;



}
