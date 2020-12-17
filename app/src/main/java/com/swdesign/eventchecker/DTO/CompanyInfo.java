package com.swdesign.eventchecker.DTO;

import com.google.gson.annotations.SerializedName;

public class CompanyInfo {
    @SerializedName("name")
    private String name;
    @SerializedName("code")
    private String englishname;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnglishname() {
        return englishname;
    }

    public void setEnglishname(String englishname) {
        this.englishname = englishname;
    }
}
