package com.swdesign.eventchecker.DTO;

import com.google.gson.annotations.SerializedName;

public class EventInfo {
    @SerializedName("company")
    private String company;
    @SerializedName("date")
    private String date;
    @SerializedName("image")
    private String imageurl;
    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private String content;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
