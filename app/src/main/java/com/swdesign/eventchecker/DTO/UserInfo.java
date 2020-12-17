package com.swdesign.eventchecker.DTO;

import com.google.gson.annotations.SerializedName;

public class UserInfo {
    @SerializedName("_id")
    private String dbid;
    @SerializedName("id")
    private String id;
    @SerializedName("passwd")
    private String passwd;

    public String getDbid() {
        return dbid;
    }

    public void setDbid(String dbid) {
        this.dbid = dbid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
