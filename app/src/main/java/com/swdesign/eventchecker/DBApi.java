package com.swdesign.eventchecker;

import com.swdesign.eventchecker.DTO.CompanyInfo;
import com.swdesign.eventchecker.DTO.EventInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DBApi {
    @GET("/event")
    Call<List<EventInfo>> getEvent(@Query("company") String company);

    @GET("/event")
    Call<List<EventInfo>> getEvent();

    @GET("/company")
    Call<List<CompanyInfo>> getCompany();
}
