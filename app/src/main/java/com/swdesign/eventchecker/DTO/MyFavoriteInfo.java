package com.swdesign.eventchecker.DTO;

import com.google.gson.annotations.SerializedName;

public class MyFavoriteInfo {
    @SerializedName("_id")
    private String id;
    @SerializedName("id")
    private String userid;
    @SerializedName("eventid")
    private String eventid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }
}
