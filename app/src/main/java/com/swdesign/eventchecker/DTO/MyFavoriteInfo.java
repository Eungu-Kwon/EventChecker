package com.swdesign.eventchecker.DTO;

import com.google.gson.annotations.SerializedName;

public class MyFavoriteInfo {
    @SerializedName("_id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }

    @SerializedName("eventid")
    private String eventid;
}
